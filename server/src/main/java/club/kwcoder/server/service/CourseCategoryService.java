package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CourseCategoryDO;
import club.kwcoder.server.dataobject.CourseCategoryDOExample;
import club.kwcoder.server.dto.CategoryDTO;
import club.kwcoder.server.dto.CourseCategoryDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CourseCategoryMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseCategoryService {

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryDOExample courseCategoryExample = new CourseCategoryDOExample();
        List<CourseCategoryDO> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategoryDO> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDTO> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDTO.class);
        pageDto.setData(courseCategoryDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseCategoryDTO courseCategoryDto) {
        CourseCategoryDO courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategoryDO.class);
        if (StringUtils.isEmpty(courseCategoryDto.getId())) {
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseCategoryDO courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 更新
     */
    private void update(CourseCategoryDO courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveBatch(String courseId, List<CategoryDTO> categorys) {
        CourseCategoryDOExample example = new CourseCategoryDOExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);

        for (CategoryDTO categoryDTO : categorys) {
            CourseCategoryDO courseCategoryDO = new CourseCategoryDO();
            courseCategoryDO
                    .setCourseId(UuidUtil.getShortUuid())
                    .setCourseId(courseId)
                    .setCategoryId(categoryDTO.getId());
            insert(courseCategoryDO);
        }
    }

    public List<CourseCategoryDTO> listByCourse(String courseId) {
        CourseCategoryDOExample example = new CourseCategoryDOExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategoryDO> courseCategoryDOS = courseCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(courseCategoryDOS, CourseCategoryDTO.class);
    }

}
