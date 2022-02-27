package pers.codewld.imall.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.config.ValidatorConfig;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.UmsRoleParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.service.UmsRoleService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 * 后台角色 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@RestController
@RequestMapping("/ums-role")
public class UmsRoleController {

    @Autowired
    UmsRoleService umsRoleService;

    @PostMapping()
    @ApiOperation("添加角色")
    public boolean add(@RequestBody @Validated(ValidatorConfig.Group.add.class) UmsRoleParam umsRoleParam) {
        return umsRoleService.add(umsRoleParam);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    public boolean del(@PathVariable @ApiParam("角色id") Long id) {
        return umsRoleService.del(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改角色")
    public boolean update(@PathVariable @ApiParam("用户id") Long id, @RequestBody @Validated UmsRoleParam umsRoleParam) {
        return umsRoleService.update(id, umsRoleParam);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询角色列表，可搜索")
    public PageVO<UmsRole> page(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
                                @RequestParam(value = "name", required = false) @Size(min = 4, max = 20, message = "用户名长度应在4-20之间") @ApiParam("名称") String name) {
        return umsRoleService.page(pageNum, pageSize, name);
    }

    @GetMapping("/list/mark")
    @ApiOperation("批量查询角色标记列表")
    public List<UmsRoleMarkVO> listMark() {
        return umsRoleService.listMark();
    }

}
