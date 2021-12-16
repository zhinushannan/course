package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.TeacherDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.TeacherService;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);
    public static final String BUSINESS_NAME = "讲师";

    @Resource
    private TeacherService teacherService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean list(@RequestBody PageDTO pageDto) {
        ResultBean resultBean = new ResultBean();
        teacherService.list(pageDto);
        resultBean.setData(pageDto);
        return resultBean;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean save(@RequestBody TeacherDTO teacherDto) {
        // 保存校验
        ValidatorUtil.require(teacherDto.getName(), "姓名");
        ValidatorUtil.length(teacherDto.getName(), "姓名", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "昵称", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "头像", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "职位", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "座右铭", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "简介", 1, 500);

        ResultBean resultBean = new ResultBean();
        teacherService.save(teacherDto);
        resultBean.setData(teacherDto);
        return resultBean;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean delete(@PathVariable String id) {
        ResultBean resultBean = new ResultBean();
        teacherService.delete(id);
        return resultBean;
    }

    @RequestMapping("/all")
    public ResultBean<List<TeacherDTO>> all() {
        List<TeacherDTO> teachers = teacherService.all();
        return ResultBean.getSuccess("查询成功", teachers);
    }

}
