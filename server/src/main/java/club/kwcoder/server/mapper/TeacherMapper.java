package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.TeacherDO;
import club.kwcoder.server.dataobject.TeacherDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {
    long countByExample(TeacherDOExample example);

    int deleteByExample(TeacherDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(TeacherDO record);

    int insertSelective(TeacherDO record);

    List<TeacherDO> selectByExample(TeacherDOExample example);

    TeacherDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TeacherDO record, @Param("example") TeacherDOExample example);

    int updateByExample(@Param("record") TeacherDO record, @Param("example") TeacherDOExample example);

    int updateByPrimaryKeySelective(TeacherDO record);

    int updateByPrimaryKey(TeacherDO record);
}