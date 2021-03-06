package pers.codewld.imall.admin.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.admin.model.entity.UmsRole;
import pers.codewld.imall.admin.model.param.UmsRoleParam;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.admin.model.vo.UmsRoleMarkVO;
import pers.codewld.imall.admin.service.UmsRoleService;
import pers.codewld.imall.common.config.ValidatorConfig;
import pers.codewld.imall.log.annotation.DisableLogDBStorage;

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
@PreAuthorize("hasAuthority('ums-role')")
@RestController
@RequestMapping("/ums/role")
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

    @DisableLogDBStorage
    @GetMapping("/page")
    @ApiOperation("分页查询，可搜索")
    public PageVO<UmsRole> page(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
                                @RequestParam(value = "name", required = false) @Size(min = 4, max = 20, message = "用户名长度应在4-20之间") @ApiParam("名称") String name,
                                @RequestParam(value = "code", required = false) @Size(min = 4, max = 20, message = "编码长度应在4-20之间") @ApiParam("编码") String code) {
        return umsRoleService.page(pageNum, pageSize, name, code);
    }

    @DisableLogDBStorage
    @PreAuthorize("hasAnyAuthority('ums-role', 'ums-admin')")
    @GetMapping("/list/mark")
    @ApiOperation("查询角色 [标记列表]")
    public List<UmsRoleMarkVO> listMark() {
        return umsRoleService.listMark();
    }

    @PutMapping("/menu/{id}")
    @ApiOperation("修改角色拥有的菜单")
    public boolean updateMenu(@PathVariable @ApiParam("角色id") Long id,
                              @RequestBody(required = false) @ApiParam("菜单id列表") List<Long> menuIdList) {
        return umsRoleService.updateMenu(id, menuIdList);
    }

    @DisableLogDBStorage
    @GetMapping("/menu/id/{id}")
    @ApiOperation("查询角色拥有的菜单 [ID列表]")
    public List<Long> listMenuId(@PathVariable @ApiParam("角色id") Long id) {
        return umsRoleService.listMenuId(id);
    }

}
