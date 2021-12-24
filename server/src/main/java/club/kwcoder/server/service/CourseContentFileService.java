package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CourseContentFileDO;
import club.kwcoder.server.dataobject.CourseContentFileDOExample;
import club.kwcoder.server.dto.CourseContentFileDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CourseContentFileMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper courseContentFileMapper;

    /**
     * 列表查询
     */
    public List<CourseContentFileDTO> list(String courseId) {
        CourseContentFileDOExample courseContentFileExample = new CourseContentFileDOExample();
        CourseContentFileDOExample.Criteria criteria = courseContentFileExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFileDO> courseContentFileList = courseContentFileMapper.selectByExample(courseContentFileExample);
        return CopyUtil.copyList(courseContentFileList, CourseContentFileDTO.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseContentFileDTO courseContentFileDto) {
        CourseContentFileDO courseContentFile = CopyUtil.copy(courseContentFileDto, CourseContentFileDO.class);
        if (StringUtils.isEmpty(courseContentFileDto.getId())) {
            this.insert(courseContentFile);
        } else {
            this.update(courseContentFile);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseContentFileDO courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }

    /**
     * 更新
     */
    private void update(CourseContentFileDO courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}
