package club.kwcoder.business.controller;


import club.kwcoder.server.dataobject.ChapterDO;
import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.service.ChapterService;
import club.kwcoder.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/15  17:02
 * @description:
 */
@RestController
public class TestController {

    @Autowired
    private ChapterService chapterService;

    @RequestMapping(value = "/chapter")
    public List<ChapterDO> test() {
        return chapterService.list();
    }

}
