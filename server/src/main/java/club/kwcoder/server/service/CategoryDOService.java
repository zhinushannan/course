package club.kwcoder.server.service;

import club.kwcoder.server.dataobject.CategoryDO;
import club.kwcoder.server.dataobject.CategoryDOExample;
import club.kwcoder.server.dto.CategoryDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.mapper.CategoryMapper;
import club.kwcoder.server.util.CopyUtil;
import club.kwcoder.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryDOService {

    @Resource
    private CategoryMapper categoryDOMapper;

    /**
     * 列表查询
     */
    public void list(PageDTO<CategoryDTO> pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.setOrderByClause("sort asc");
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
        PageInfo<CategoryDO> pageInfo = new PageInfo<>(categoryDOList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDTO> categoryDODtoList = CopyUtil.copyList(categoryDOList, CategoryDTO.class);
        pageDto.setData(categoryDODtoList);
    }

    public List<CategoryDTO> all() {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.setOrderByClause("sort asc");
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(categoryDOExample);
        return CopyUtil.copyList(categoryDOList, CategoryDTO.class);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CategoryDTO categoryDODto) {
        CategoryDO categoryDO = CopyUtil.copy(categoryDODto, CategoryDO.class);
        if (StringUtils.isEmpty(categoryDODto.getId())) {
            this.insert(categoryDO);
        } else {
            this.update(categoryDO);
        }
    }

    /**
     * 新增
     */
    private void insert(CategoryDO categoryDO) {
        categoryDO.setId(UuidUtil.getShortUuid());
        categoryDOMapper.insert(categoryDO);
    }

    /**
     * 更新
     */
    private void update(CategoryDO categoryDO) {
        categoryDOMapper.updateByPrimaryKey(categoryDO);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        categoryDOMapper.deleteByPrimaryKey(id);
    }
}
