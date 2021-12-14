package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.SectionDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.dto.SectionPageDTO;
import club.kwcoder.server.service.SectionService;
import club.kwcoder.server.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "小节";

    @Resource
    private SectionService sectionService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean<SectionPageDTO> list(@RequestBody SectionPageDTO pageDto) {
        ValidatorUtil.require(pageDto.getCourseId(), "课程ID");
        ValidatorUtil.require(pageDto.getChapterId(), "大章ID");
        sectionService.list(pageDto);
        return ResultBean.getSuccess("查询成功", pageDto);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean<SectionDTO> save(@RequestBody SectionDTO sectionDto) {
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);

        sectionService.save(sectionDto);
        return ResultBean.getSuccess("保存成功", sectionDto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable String id) {
        sectionService.delete(id);
        return ResultBean.getSuccess("删除成功", null);
    }
}
