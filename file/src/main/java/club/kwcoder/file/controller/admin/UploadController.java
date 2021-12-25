package club.kwcoder.file.controller.admin;

import club.kwcoder.server.dataobject.FileDO;
import club.kwcoder.server.dto.FileDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.enums.FileUseEnum;
import club.kwcoder.server.service.FileService;
import club.kwcoder.server.util.Base64ToMultipartFile;
import club.kwcoder.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: zhinushannan
 * @create: 2021/12/16  22:54
 * @description:
 */
@RestController
@RequestMapping("/admin")
public class UploadController {

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Autowired
    private FileService fileService;

    public static final String BUSINESS_NAME = "文件上传";

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping("/upload")
    public ResultBean<FileDTO> upload(@RequestBody FileDTO fileDTO) throws Exception {
        LOG.info("上传文件开始");

        String key = fileDTO.getKey();
        String suffix = fileDTO.getSuffix();
        String use = fileDTO.getUse();
        String shardBase64 = fileDTO.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        // 如果文件夹不存在则创建
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = new StringBuffer()
                .append(dir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDTO.getShardIndex())
                .toString();

        String fullPath = FILE_PATH + localPath;

        File dest = new File(fullPath);


        assert shard != null;
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        fileDTO.setPath(path);
        fileService.save(fileDTO);

        fileDTO.setPath(FILE_DOMAIN + path);

        if (Objects.equals(fileDTO.getShardIndex(), fileDTO.getShardTotal())) {
            this.merge(fileDTO);
        }

        return ResultBean.getSuccess("上传成功", fileDTO);
    }

    public void merge(FileDTO fileDTO) throws Exception {
        LOG.info("合并分片开始");
        String path = fileDTO.getPath(); //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
        path = path.replace(FILE_DOMAIN, ""); //course\6sfSqfOwzmik4A4icMYuUe.mp4
        Integer shardTotal = fileDTO.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                fileInputStream = new FileInputStream(FILE_PATH + path + "." + (i + 1)); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }
        LOG.info("合并分片结束");

        System.gc();
        Thread.sleep(100);

        // 删除分片
        LOG.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        LOG.info("删除分片结束");
    }

    @GetMapping("/check/{key}")
    public ResultBean<FileDTO> check(@PathVariable String key) {
        LOG.info("检查上传分片开始：{}", key);
        FileDTO fileDTO = fileService.findByKey(key);
        if (fileDTO != null) {
            fileDTO.setPath(FILE_DOMAIN + fileDTO.getPath());
        }
        return ResultBean.getSuccess("检查成功！", fileDTO);
    }

}
