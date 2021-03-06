package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.SectionDO;
import club.kwcoder.server.dataobject.SectionDOExample;
import club.kwcoder.server.dto.SectionDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.SectionPageDTO;
import club.kwcoder.server.mapper.SectionMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    @Autowired
    private CourseService courseService;

    /**
     * 列表查询
     */
    public void list(SectionPageDTO pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SectionDOExample sectionExample = new SectionDOExample();
        SectionDOExample.Criteria criteria = sectionExample.createCriteria();

        if (StringUtils.isNotBlank(pageDto.getCourseId()) || StringUtils.isNotBlank(pageDto.getChapterId())) {
            criteria.andCourseIdEqualTo(pageDto.getCourseId())
                    .andCourseIdEqualTo(pageDto.getChapterId());
        }


        sectionExample.setOrderByClause("sort asc");
        List<SectionDO> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<SectionDO> pageInfo = new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDTO> sectionDtoList = CopyUtil.copyList(sectionList, SectionDTO.class);
        pageDto.setData(sectionDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    // 开启事务，默认只回滚 RuntimeException
    // @Transactional(rollbackFor = Exception.class) 开启针对Exception及其子类的回滚
    /*
    如下情况无法回滚：
    1、 在catch语句块中有return
    2、 该方法被其他方法调用时
     */
    @Transactional
    public void save(SectionDTO sectionDto) {
        SectionDO section = CopyUtil.copy(sectionDto, SectionDO.class);
        if (StringUtils.isEmpty(sectionDto.getId())) {
            this.insert(section);
        } else {
            this.update(section);
        }
        courseService.updateTime(sectionDto.getCourseId());
    }

    /**
     * 新增
     */
    private void insert(SectionDO section) {
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 更新
     */
    private void update(SectionDO section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
