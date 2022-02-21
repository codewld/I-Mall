package pers.codewld.imall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.config.ValidatorConfig;
import pers.codewld.imall.model.param.UmsAdminParam;
import pers.codewld.imall.model.vo.PageVO;
import pers.codewld.imall.model.vo.UmsAdminVO;
import pers.codewld.imall.service.UmsAdminService;

import javax.validation.constraints.Min;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Validated
@RestController
@RequestMapping("/ums-admin")
public class UmsAdminController {

    @Autowired
    UmsAdminService umsAdminService;

    @PostMapping()
    @ApiOperation("新增用户")
    public Object add(@RequestBody @Validated(ValidatorConfig.Group.add.class) UmsAdminParam umsAdminParam) {
        return umsAdminService.add(umsAdminParam);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Boolean del(@PathVariable @ApiParam("用户id") Long id) {
        return umsAdminService.removeById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户")
    public Boolean update(@PathVariable @ApiParam("用户id") Long id, @RequestBody @Validated UmsAdminParam umsAdminParam) {
        return umsAdminService.update(id, umsAdminParam);
    }

    @GetMapping("/list")
    @ApiOperation("分页查询用户列表")
    public PageVO<UmsAdminVO> list(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize) throws InterruptedException {
        Thread.currentThread().sleep(1000);
        return umsAdminService.page(pageNum, pageSize);
    }

}
