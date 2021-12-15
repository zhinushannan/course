package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.CourseContentDO;
import club.kwcoder.server.dataobject.CourseContentDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseContentMapper {
    long countByExample(CourseContentDOExample example);

    int deleteByExample(CourseContentDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseContentDO record);

    int insertSelective(CourseContentDO record);

    List<CourseContentDO> selectByExample(CourseContentDOExample example);

    CourseContentDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseContentDO record, @Param("example") CourseContentDOExample example);

    int updateByExample(@Param("record") CourseContentDO record, @Param("example") CourseContentDOExample example);

    int updateByPrimaryKeySelective(CourseContentDO record);

    int updateByPrimaryKey(CourseContentDO record);
}