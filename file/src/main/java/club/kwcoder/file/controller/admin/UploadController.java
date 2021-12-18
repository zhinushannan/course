package club.kwcoder.file.controller.admin;

import club.kwcoder.server.dto.FileDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.enums.FileUseEnum;
import club.kwcoder.server.service.FileService;
import club.kwcoder.server.util.UuidUtil;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

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
    public ResultBean<FileDTO> upload(@RequestParam MultipartFile file, String use) throws IOException {
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String fileName = file.getOriginalFilename();

        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = dir + "/" + key + "." + suffix;

        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setPath(path);
        fileDTO.setName(fileName);
        fileDTO.setSize(Math.toIntExact(file.getSize()));
        fileDTO.setSuffix(suffix);
        fileDTO.setUse(use);
        fileService.save(fileDTO);

        fileDTO.setPath(FILE_DOMAIN + path);
        return ResultBean.getSuccess("上传成功", fileDTO);
    }

}
