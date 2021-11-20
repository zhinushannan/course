package club.kwcoder.server.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import club.kwcoder.server.dataobject.ChapterDO;
import club.kwcoder.server.dataobject.ChapterDOExample;
import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.dto.ChapterDTO;
import club.kwcoder.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ChapterDTO> list() {
        PageHelper.startPage(1, 1);
        ChapterDOExample example = new ChapterDOExample();
        example.setOrderByClause("id asc");
        List<ChapterDO> chapterDOS = chapterMapper.selectByExample(example);

        List<ChapterDTO> chapterDTOS = new ArrayList<>();
        for (ChapterDO chapterDO : chapterDOS) {
            ChapterDTO chapterDTO = new ChapterDTO();
            BeanUtils.copyProperties(chapterDO, chapterDTO);
            chapterDTOS.add(chapterDTO);
        }

        return chapterDTOS;
    }

}
