package club.kwcoder.server.mapper.my;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);
}
