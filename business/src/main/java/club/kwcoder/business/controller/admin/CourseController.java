package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.CourseDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.CourseService;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean<PageDTO<CourseDTO>> list(@RequestBody PageDTO<CourseDTO> pageDto) {
        courseService.list(pageDto);
        return ResultBean.getSuccess("查询成功", pageDto);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean<CourseDTO> save(@RequestBody CourseDTO courseDto) {
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        courseService.save(courseDto);
        return ResultBean.getSuccess("保存成功！", courseDto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable String id) {
        courseService.delete(id);
        return ResultBean.getSuccess("删除成功", null);
    }
}
