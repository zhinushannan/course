package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.ChapterDO;
import club.kwcoder.server.dataobject.ChapterDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChapterMapper {
    long countByExample(ChapterDOExample example);

    int deleteByExample(ChapterDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(ChapterDO record);

    int insertSelective(ChapterDO record);

    List<ChapterDO> selectByExample(ChapterDOExample example);

    ChapterDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ChapterDO record, @Param("example") ChapterDOExample example);

    int updateByExample(@Param("record") ChapterDO record, @Param("example") ChapterDOExample example);

    int updateByPrimaryKeySelective(ChapterDO record);

    int updateByPrimaryKey(ChapterDO record);
}