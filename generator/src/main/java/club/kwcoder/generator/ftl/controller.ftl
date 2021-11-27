package club.kwcoder.${module}.controller.admin;

import club.kwcoder.server.dto.${Domain}DTO;
import club.kwcoder.server.dto.PageDTO;
import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.service.${Domain}Service;
import club.kwcoder.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
    public static final String BUSINESS_NAME = "${tableNameCn}";

    @Resource
    private ${Domain}Service ${domain}Service;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResultBean list(@RequestBody PageDTO pageDto) {
        ResultBean resultBean = new ResultBean();
        ${domain}Service.list(pageDto);
        resultBean.setData(pageDto);
        return resultBean;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResultBean save(@RequestBody ${Domain}DTO ${domain}Dto) {
        // 保存校验
        <#list fieldList as field>
        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
            <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
            </#if>
            <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
            </#if>
        </#if>
        </#list>

        ResultBean resultBean = new ResultBean();
        ${domain}Service.save(${domain}Dto);
        resultBean.setData(${domain}Dto);
        return resultBean;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResultBean delete(@PathVariable String id) {
        ResultBean resultBean = new ResultBean();
        ${domain}Service.delete(id);
        return resultBean;
    }
}
