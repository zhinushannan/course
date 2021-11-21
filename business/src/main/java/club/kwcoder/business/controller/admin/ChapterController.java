package club.kwcoder.business.controller.admin;


import club.kwcoder.server.dto.ChapterDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.exception.ValidatorException;
import club.kwcoder.server.service.ChapterService;
import club.kwcoder.server.utils.ValidatorUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: zhinushannan
 * @create: 2021/11/15  17:02
 * @description:
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    public static final String BUSINESS_NAME = "大章";

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultBean<PageDTO<ChapterDTO>> list(@RequestBody PageDTO<ChapterDTO> pageDTO) {
        chapterService.list(pageDTO);
        return ResultBean.getSuccess("查询成功！", pageDTO);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultBean<ChapterDTO> save(@RequestBody ChapterDTO chapterDTO) {

        ValidatorUtil.require(chapterDTO.getName(), "名称");
        ValidatorUtil.require(chapterDTO.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDTO.getCourseId(), "课程ID", 1, 8);

        chapterService.save(chapterDTO);
        return ResultBean.getSuccess("新增成功！", chapterDTO);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResultBean<String> delete(@PathVariable String id) {
        chapterService.delete(id);
        return ResultBean.getSuccess("删除成功！", "删除成功！");
    }

}
