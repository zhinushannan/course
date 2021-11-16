package club.kwcoder.server.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import club.kwcoder.server.dataobject.ChapterDO;
import club.kwcoder.server.dataobject.ChapterDOExample;
import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.mapper.ChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/16  08:03
 * @description:
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    public List<ChapterDO> list() {
        return chapterMapper.selectByExample(new ChapterDOExample());
    }

}
