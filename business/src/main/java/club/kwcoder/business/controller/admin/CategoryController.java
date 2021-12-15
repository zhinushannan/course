package club.kwcoder.business.controller.admin;

import club.kwcoder.server.dto.CategoryDTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.CategoryDOService;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "分类";

    @Resource
    private CategoryDOService categoryDOService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean<PageDTO<CategoryDTO>> list(@RequestBody PageDTO<CategoryDTO> pageDto) {
        categoryDOService.list(pageDto);
        return ResultBean.getSuccess("查询成功", pageDto);
    }

    @PostMapping("/all")
    public ResultBean<List<CategoryDTO>> all() {
        List<CategoryDTO> all = categoryDOService.all();
        return ResultBean.getSuccess("查询成功", all);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean<CategoryDTO> save(@RequestBody CategoryDTO categoryDODto) {
        // 保存校验
        ValidatorUtil.require(categoryDODto.getParent(), "父id");
        ValidatorUtil.require(categoryDODto.getName(), "名称");
        ValidatorUtil.length(categoryDODto.getName(), "名称", 1, 50);

        categoryDOService.save(categoryDODto);
        return ResultBean.getSuccess("保存成功", categoryDODto);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean<String> delete(@PathVariable String id) {
        categoryDOService.delete(id);
        return ResultBean.getSuccess("删除成功", null);
    }
}
