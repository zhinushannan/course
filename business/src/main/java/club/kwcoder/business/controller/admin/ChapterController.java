package club.kwcoder.business.controller.admin;


import club.kwcoder.server.dto.ChapterDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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
    public PageDTO<ChapterDTO> test(@RequestBody PageDTO<ChapterDTO> pageDTO) {
        chapterService.list(pageDTO);
        return pageDTO;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ChapterDTO save(@RequestBody ChapterDTO chapterDTO) {
        chapterService.save(chapterDTO);
        return chapterDTO;
    }

}
