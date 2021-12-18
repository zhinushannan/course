package club.kwcoder.server.mapper;

import club.kwcoder.server.dataobject.FileDO;
import club.kwcoder.server.dataobject.FileDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {
    long countByExample(FileDOExample example);

    int deleteByExample(FileDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileDO record);

    int insertSelective(FileDO record);

    List<FileDO> selectByExample(FileDOExample example);

    FileDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileDO record, @Param("example") FileDOExample example);

    int updateByExample(@Param("record") FileDO record, @Param("example") FileDOExample example);

    int updateByPrimaryKeySelective(FileDO record);

    int updateByPrimaryKey(FileDO record);
}