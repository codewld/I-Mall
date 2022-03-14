package pers.codewld.imall.admin.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.admin.model.entity.UmsMenu;
import pers.codewld.imall.admin.model.param.UmsMenuParam;
import pers.codewld.imall.admin.model.vo.UmsMenuMarkVO;
import pers.codewld.imall.admin.service.UmsMenuService;
import pers.codewld.imall.common.config.ValidatorConfig;

import java.util.List;

/**
 * <p>
 * 后台菜单 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@PreAuthorize("hasAuthority('ums-menu')")
@Validated
@RestController
@RequestMapping("/ums/menu")
public class UmsMenuController {

    @Autowired
    UmsMenuService umsMenuService;

    @PostMapping()
    @ApiOperation("添加菜单")
    public boolean add(@RequestBody @Validated(ValidatorConfig.Group.add.class) @ApiParam("菜单参数") UmsMenuParam umsMenuParam) {
        return umsMenuService.add(umsMenuParam);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除菜单")
    public boolean del(@PathVariable @ApiParam("菜单id") Long id) {
        return umsMenuService.del(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改菜单")
    public boolean update(@PathVariable @ApiParam("菜单id") Long id,
                          @RequestBody @Validated @ApiParam("菜单参数") UmsMenuParam umsMenuParam) {
        return umsMenuService.update(id, umsMenuParam);
    }

    @GetMapping("/tree")
    @ApiOperation("查询树形结构的菜单")
    List<UmsMenu> tree() {
        return umsMenuService.tree();
    }

    @PreAuthorize("hasAnyAuthority('ums-menu', 'ums-role')")
    @GetMapping("/tree/mark")
    @ApiOperation("查询树形结构的菜单 [标记形式]")
    List<UmsMenuMarkVO> treeMark() {
        return umsMenuService.treeMark();
    }
}
