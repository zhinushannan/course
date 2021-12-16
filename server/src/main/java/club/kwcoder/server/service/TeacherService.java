package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.TeacherDO;
import club.kwcoder.server.dataobject.TeacherDOExample;
import club.kwcoder.server.dto.TeacherDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.TeacherMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        TeacherDOExample teacherExample = new TeacherDOExample();
        List<TeacherDO> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<TeacherDO> pageInfo = new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDTO> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDTO.class);
        pageDto.setData(teacherDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(TeacherDTO teacherDto) {
        TeacherDO teacher = CopyUtil.copy(teacherDto, TeacherDO.class);
        if (StringUtils.isEmpty(teacherDto.getId())) {
            this.insert(teacher);
        } else {
            this.update(teacher);
        }
    }

    /**
     * 新增
     */
    private void insert(TeacherDO teacher) {
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }

    /**
     * 更新
     */
    private void update(TeacherDO teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    public List<TeacherDTO> all() {
        TeacherDOExample example = new TeacherDOExample();
        List<TeacherDO> teacherDOS = teacherMapper.selectByExample(example);
        return CopyUtil.copyList(teacherDOS, TeacherDTO.class);
    }
}
