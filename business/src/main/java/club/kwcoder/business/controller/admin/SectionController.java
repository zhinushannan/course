package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.SectionDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.SectionService;
import club.kwcoder.server.util.ValidatorUtil;
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
    public ResultBean list(@RequestBody PageDTO pageDto) {
        ResultBean resultBean = new ResultBean();
        sectionService.list(pageDto);
        resultBean.setData(pageDto);
        return resultBean;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean save(@RequestBody SectionDTO sectionDto) {
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);

        ResultBean resultBean = new ResultBean();
        sectionService.save(sectionDto);
        resultBean.setData(sectionDto);
        return resultBean;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean delete(@PathVariable String id) {
        ResultBean resultBean = new ResultBean();
        sectionService.delete(id);
        return resultBean;
    }
}
