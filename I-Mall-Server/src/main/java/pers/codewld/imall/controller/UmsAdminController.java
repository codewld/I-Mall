package pers.codewld.imall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.imall.model.entity.UmsAdmin;
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

    @GetMapping("/list")
    @ApiOperation("分页查询用户列表")
    public PageVO<UmsAdminVO> list(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize) throws InterruptedException {
        Thread.currentThread().sleep(1000);
        return umsAdminService.page(pageNum, pageSize);
    }

}
