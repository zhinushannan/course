package club.kwcoder.server.service;/**
 * @author zhinushannan
 * @date 2021/11/16 8:03
 * @subject
 */

import club.kwcoder.server.dataobject.ChapterDO;
import club.kwcoder.server.dataobject.ChapterDOExample;
import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.dto.ChapterDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.ChapterMapper;
import club.kwcoder.server.utils.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: zhinushannan
 * @create: 2021/11/16  08:03
 * @description:
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    public void list(PageDTO<ChapterDTO> pageDTO) {
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getSize());
        ChapterDOExample example = new ChapterDOExample();
        example.setOrderByClause("id asc");
        List<ChapterDO> chapterDOS = chapterMapper.selectByExample(example);

        PageInfo<ChapterDO> pageInfo = new PageInfo<>(chapterDOS);
        pageDTO.setTotal(pageInfo.getTotal());

        List<ChapterDTO> chapterDTOS = new ArrayList<>();
        for (ChapterDO chapterDO : chapterDOS) {
            ChapterDTO chapterDTO = new ChapterDTO();
            BeanUtils.copyProperties(chapterDO, chapterDTO);
            chapterDTOS.add(chapterDTO);
        }

        pageDTO.setData(chapterDTOS);
    }

    public void save(ChapterDTO chapterDTO) {
        ChapterDO chapterDO = CopyUtil.copy(chapterDTO, ChapterDO.class);
        if (StringUtils.isBlank(chapterDTO.getId())) {
            this.insert(chapterDO);
        } else {
            this.update(chapterDO);
        }
    }

    private void insert(ChapterDO chapterDO) {
        chapterDO.setId(UUID.randomUUID().toString().split("-")[0]);
        chapterMapper.insert(chapterDO);
    }

    private void update(ChapterDO chapterDO) {
        chapterMapper.updateByPrimaryKey(chapterDO);
    }

    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
