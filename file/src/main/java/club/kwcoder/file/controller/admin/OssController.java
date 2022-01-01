package club.kwcoder.file.controller.admin;

import club.kwcoder.server.dto.FileDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.enums.FileUseEnum;
import club.kwcoder.server.service.FileService;
import club.kwcoder.server.util.Base64ToMultipartFile;
import club.kwcoder.server.util.UuidUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class OssController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.domain}")
    private String ossDomain;

    public static final String BUSINESS_NAME = "文件上传";

    @Autowired
    private FileService fileService;

    @PostMapping("/oss-append")
    public ResultBean<FileDTO> fileUpload(@RequestBody FileDTO fileDTO) throws IOException {
        LOG.info("上传文件开始");
        String use = fileDTO.getUse();
        String key = fileDTO.getKey();
        String suffix = fileDTO.getSuffix();
        Integer shardIndex = fileDTO.getShardIndex();
        Integer shardSize = fileDTO.getShardSize();
        String shardBase64 = fileDTO.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();

        String path = dir +
                "/" +
                key +
                "." +
                suffix; // course\6sfSqfOwzmik4A4icMYuUe.mp4

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");

        // 通过AppendObjectRequest设置多个参数。
        assert shard != null;
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucket, path, new ByteArrayInputStream(shard.getBytes()),meta);

        // 第一次追加。
        // 设置文件的追加位置。
        appendObjectRequest.setPosition(((long) (shardIndex - 1) * shardSize));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        // 文件的64位CRC值。此值根据ECMA-182标准计算得出。
        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

        // 关闭OSSClient。
        ossClient.shutdown();

        LOG.info("保存文件记录开始");
        fileDTO.setPath(path);

        fileService.save(fileDTO);

        fileDTO.setPath(ossDomain + path);

        return ResultBean.getSuccess("追加成功！", fileDTO);
    }

    @PostMapping("/oss-simple")
    public ResultBean<FileDTO> fileUpload(@RequestParam MultipartFile file, String use) throws Exception {
        LOG.info("上传文件开始");
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        assert useEnum != null;
        String dir = useEnum.name().toLowerCase();
        String path = dir + "/" + key + "." + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

        FileDTO fileDto = new FileDTO();

        fileService.save(fileDto);

        fileDto.setPath(ossDomain + path);

        return ResultBean.getSuccess("上传成功！", fileDto);
    }



}
