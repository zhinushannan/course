package club.kwcoder.business.controller.admin;


import club.kwcoder.server.dto.ChapterDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.ChapterService;
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

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultBean<PageDTO<ChapterDTO>> test(@RequestBody PageDTO<ChapterDTO> pageDTO) {
        chapterService.list(pageDTO);
        return ResultBean.getSuccess("查询成功！", pageDTO);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultBean<ChapterDTO> save(@RequestBody ChapterDTO chapterDTO) {
        chapterService.save(chapterDTO);
        return ResultBean.getSuccess("新增成功！", chapterDTO);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResultBean<String> delete(@PathVariable String id) {
        chapterService.delete(id);
        return ResultBean.getSuccess("删除成功！", "删除成功！");
    }

}
