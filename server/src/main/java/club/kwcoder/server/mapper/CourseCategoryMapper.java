package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.CourseCategoryDO;
import club.kwcoder.server.dataobject.CourseCategoryDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseCategoryMapper {
    long countByExample(CourseCategoryDOExample example);

    int deleteByExample(CourseCategoryDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseCategoryDO record);

    int insertSelective(CourseCategoryDO record);

    List<CourseCategoryDO> selectByExample(CourseCategoryDOExample example);

    CourseCategoryDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseCategoryDO record, @Param("example") CourseCategoryDOExample example);

    int updateByExample(@Param("record") CourseCategoryDO record, @Param("example") CourseCategoryDOExample example);

    int updateByPrimaryKeySelective(CourseCategoryDO record);

    int updateByPrimaryKey(CourseCategoryDO record);
}