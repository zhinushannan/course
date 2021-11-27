package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.SectionDO;
import club.kwcoder.server.dataobject.SectionDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SectionMapper {
    long countByExample(SectionDOExample example);

    int deleteByExample(SectionDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(SectionDO record);

    int insertSelective(SectionDO record);

    List<SectionDO> selectByExample(SectionDOExample example);

    SectionDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SectionDO record, @Param("example") SectionDOExample example);

    int updateByExample(@Param("record") SectionDO record, @Param("example") SectionDOExample example);

    int updateByPrimaryKeySelective(SectionDO record);

    int updateByPrimaryKey(SectionDO record);
}