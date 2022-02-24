package pers.codewld.imall.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import pers.codewld.imall.model.entity.UmsMenu;
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

    @GetMapping("/list")
    @ApiOperation("批量查询位于根结点的菜单")
    List<UmsMenu> listRoot() {
        return umsMenuService.listRoot();
    }

    @GetMapping("/list/{id}")
    @ApiOperation("批量查询父结点下的子菜单")
    List<UmsMenu> listSon(@PathVariable @ApiParam("菜单ID") Long id) {
        return umsMenuService.listSon(id);
    }

}
