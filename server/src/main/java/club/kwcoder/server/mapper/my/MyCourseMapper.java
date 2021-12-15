package club.kwcoder.server.mapper.my;

import club.kwcoder.server.dto.SortDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    void updateSort(SortDTO sort);

    void moveSortsForward(SortDTO sort);

    void moveSortsBackward(SortDTO sort);
}
