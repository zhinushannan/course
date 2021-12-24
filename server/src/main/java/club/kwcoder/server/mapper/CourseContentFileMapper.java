package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.CourseContentFileDO;
import club.kwcoder.server.dataobject.CourseContentFileDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseContentFileMapper {
    long countByExample(CourseContentFileDOExample example);

    int deleteByExample(CourseContentFileDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseContentFileDO record);

    int insertSelective(CourseContentFileDO record);

    List<CourseContentFileDO> selectByExample(CourseContentFileDOExample example);

    CourseContentFileDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseContentFileDO record, @Param("example") CourseContentFileDOExample example);

    int updateByExample(@Param("record") CourseContentFileDO record, @Param("example") CourseContentFileDOExample example);

    int updateByPrimaryKeySelective(CourseContentFileDO record);

    int updateByPrimaryKey(CourseContentFileDO record);
}