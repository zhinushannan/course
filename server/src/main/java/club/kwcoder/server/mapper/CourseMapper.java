package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.CourseDO;
import club.kwcoder.server.dataobject.CourseDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper {
    long countByExample(CourseDOExample example);

    int deleteByExample(CourseDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseDO record);

    int insertSelective(CourseDO record);

    List<CourseDO> selectByExample(CourseDOExample example);

    CourseDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseDO record, @Param("example") CourseDOExample example);

    int updateByExample(@Param("record") CourseDO record, @Param("example") CourseDOExample example);

    int updateByPrimaryKeySelective(CourseDO record);

    int updateByPrimaryKey(CourseDO record);
}