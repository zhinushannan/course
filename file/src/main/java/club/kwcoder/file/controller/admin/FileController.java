package club.kwcoder.file.controller.admin;

import club.kwcoder.server.dto.FileDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.FileService;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    public static final String BUSINESS_NAME = "文件";

    @Resource
    private FileService fileService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean<PageDTO<FileDTO>> list(@RequestBody PageDTO<FileDTO> pageDto) {
        fileService.list(pageDto);
        return ResultBean.getSuccess("查询成功", pageDto);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean<FileDTO> save(@RequestBody FileDTO fileDto) {
        // 保存校验
        ValidatorUtil.require(fileDto.getPath(), "相对路径");
        ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
        ValidatorUtil.length(fileDto.getKey(), "文件标识", 1, 32);

        return ResultBean.getSuccess("新增成功", fileDto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable String id) {
        fileService.delete(id);
        return ResultBean.getSuccess("删除成功", null);
    }
}
