package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CourseDO;
import club.kwcoder.server.dataobject.CourseDOExample;
import club.kwcoder.server.dto.CourseDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CourseMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO<CourseDTO> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseDOExample courseExample = new CourseDOExample();
        courseExample.setOrderByClause("sort asc");
        List<CourseDO> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<CourseDO> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDTO> courseDtoList = CopyUtil.copyList(courseList, CourseDTO.class);
        pageDto.setData(courseDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseDTO courseDto) {
        CourseDO course = CopyUtil.copy(courseDto, CourseDO.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseDO course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    /**
     * 更新
     */
    private void update(CourseDO course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }
}
