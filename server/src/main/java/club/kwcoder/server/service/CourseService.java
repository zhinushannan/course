package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CourseCategoryDO;
import club.kwcoder.server.dataobject.CourseContentDO;
import club.kwcoder.server.dataobject.CourseDO;
import club.kwcoder.server.dataobject.CourseDOExample;
import club.kwcoder.server.dto.CourseContentDTO;
import club.kwcoder.server.dto.CourseDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CourseContentMapper;
import club.kwcoder.server.mapper.CourseMapper;
import club.kwcoder.server.mapper.my.MyCourseMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper courseMapper;

    @Autowired
    private MyCourseMapper myCourseMapper;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private CourseContentMapper courseContentMapper;

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
    @Transactional
    public void save(CourseDTO courseDto) {
        CourseDO course = CopyUtil.copy(courseDto, CourseDO.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }
        // 批量保存分类
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());

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

    public void updateTime(String courseId) {
        LOG.info("更新课程时长：{}", courseId);
        myCourseMapper.updateTime(courseId);
    }

    /**
     * 查询课程内容
     * @param id
     * @return
     */
    public CourseContentDTO findContent(String id) {
        CourseContentDO courseContentDO = courseContentMapper.selectByPrimaryKey(id);
        if (null == courseContentDO) {
            return null;
        }
        return CopyUtil.copy(courseContentDO, CourseContentDTO.class);
    }

    /**
     * 保存课程内容，包含新增和修改
     * @return
     */
    public int saveContent(CourseContentDTO courseContentDTO) {
        CourseContentDO copy = CopyUtil.copy(courseContentDTO, CourseContentDO.class);
        int i = courseContentMapper.updateByPrimaryKey(copy);
        if (0 == i) {
            i = courseContentMapper.insert(copy);
        }
        return i;
    }

}
