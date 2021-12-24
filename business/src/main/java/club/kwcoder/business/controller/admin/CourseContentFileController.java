package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.CourseContentFileDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.CourseContentFileService;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")
public class CourseContentFileController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseContentFileController.class);
    public static final String BUSINESS_NAME = "课程内容文件";

    @Resource
    private CourseContentFileService courseContentFileService;

    /**
     * 列表查询
     */
    @GetMapping("/list/{courseId}")
    public ResultBean<List<CourseContentFileDTO>> list(@PathVariable String courseId) {
        List<CourseContentFileDTO> courseContentFileDTOS = courseContentFileService.list(courseId);
        return ResultBean.getSuccess("查询成功", courseContentFileDTOS);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean<CourseContentFileDTO> save(@RequestBody CourseContentFileDTO courseContentFileDto) {
        // 保存校验
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程id");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);

        courseContentFileService.save(courseContentFileDto);
        return ResultBean.getSuccess("保存成功", courseContentFileDto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable String id) {
        courseContentFileService.delete(id);
        return ResultBean.getSuccess("删除成功", null);
    }
}
