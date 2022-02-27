package pers.codewld.imall.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.config.ValidatorConfig;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.param.UmsMenuParam;
import pers.codewld.imall.model.vo.UmsMenuMarkVO;
import pers.codewld.imall.service.UmsMenuService;

import java.util.List;

/**
 * <p>
 * 后台菜单 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Validated
@RestController
@RequestMapping("/ums-menu")
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

    @GetMapping("/list/root")
    @ApiOperation("批量查询位于根结点的菜单")
    List<UmsMenu> listRoot() {
        return umsMenuService.listRoot();
    }

    @GetMapping("/list/{id}")
    @ApiOperation("批量查询父结点下的子菜单")
    List<UmsMenu> listSon(@PathVariable @ApiParam("菜单ID") Long id) {
        return umsMenuService.listSon(id);
    }

    @GetMapping("/list/mark")
    @ApiOperation("批量查询菜单标记列表，树形结构")
    List<UmsMenuMarkVO> listMark() {
        return umsMenuService.listMark();
    }

    @GetMapping("/list/mark/{id}")
    @ApiOperation("批量查询父结点下的子菜单标记")
    List<UmsMenuMarkVO> listSonMark(@PathVariable @ApiParam("菜单ID") Long id) {
        return umsMenuService.listSonMark(id);
    }

}
