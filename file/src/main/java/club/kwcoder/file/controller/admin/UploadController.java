package club.kwcoder.file.controller.admin;

import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: zhinushannan
 * @create: 2021/12/16  22:54
 * @description:
 */
@RestController
@RequestMapping("/admin")
public class UploadController {

    public static final String BUSINESS_NAME = "文件上传";

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping("/upload")
    public ResultBean<String> upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始：{}", file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = "C:/CODE/course/file_dir/teacher/" + key + "-" + fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        return ResultBean.getSuccess("上传成功", null);
    }

}
