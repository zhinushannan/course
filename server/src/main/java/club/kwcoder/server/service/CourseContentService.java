package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CourseContentDO;
import club.kwcoder.server.dataobject.CourseContentDOExample;
import club.kwcoder.server.dto.CourseContentDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CourseContentMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseContentService {

    @Resource
    private CourseContentMapper courseContentMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseContentDOExample courseContentExample = new CourseContentDOExample();
        List<CourseContentDO> courseContentList = courseContentMapper.selectByExample(courseContentExample);
        PageInfo<CourseContentDO> pageInfo = new PageInfo<>(courseContentList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseContentDTO> courseContentDtoList = CopyUtil.copyList(courseContentList, CourseContentDTO.class);
        pageDto.setData(courseContentDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseContentDTO courseContentDto) {
        CourseContentDO courseContent = CopyUtil.copy(courseContentDto, CourseContentDO.class);
        if (StringUtils.isEmpty(courseContentDto.getId())) {
            this.insert(courseContent);
        } else {
            this.update(courseContent);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseContentDO courseContent) {
        courseContent.setId(UuidUtil.getShortUuid());
        courseContentMapper.insert(courseContent);
    }

    /**
     * 更新
     */
    private void update(CourseContentDO courseContent) {
        courseContentMapper.updateByPrimaryKey(courseContent);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseContentMapper.deleteByPrimaryKey(id);
    }
}
