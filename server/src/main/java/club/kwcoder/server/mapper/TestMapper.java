package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.dataobject.TestDOExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestMapper {
    long countByExample(TestDOExample example);

    int deleteByExample(TestDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestDO record);

    int insertSelective(TestDO record);

    List<TestDO> selectByExample(TestDOExample example);

    TestDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestDO record, @Param("example") TestDOExample example);

    int updateByExample(@Param("record") TestDO record, @Param("example") TestDOExample example);

    int updateByPrimaryKeySelective(TestDO record);

    int updateByPrimaryKey(TestDO record);
}