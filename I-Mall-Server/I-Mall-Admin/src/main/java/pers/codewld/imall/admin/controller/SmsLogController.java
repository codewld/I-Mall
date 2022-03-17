package pers.codewld.imall.admin.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.log.model.entity.ControllerLog;
import pers.codewld.imall.log.service.ControllerLogService;

import javax.validation.constraints.Min;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-03-17
 */
@Validated
@RestController
@RequestMapping("/sms/log")
public class SmsLogController {

    @Autowired
    ControllerLogService controllerLogService;

    @GetMapping("/page")
    @ApiOperation("分页查询，可搜索")
    public PageVO<ControllerLog> page(@RequestParam(value = "pageNum", defaultValue = "1") @Min(value = 1, message = "页数最小为1") @ApiParam("当前页数") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "5") @Min(value = 1, message = "每页条数最小为1") @ApiParam("每页条数") Integer pageSize,
                                      @RequestParam(value = "summary", required = false) @ApiParam("接口概述") String summary,
                                      @RequestParam(value = "username", required = false) @ApiParam("用户名") String username) {
        return controllerLogService.page(pageNum, pageSize, summary, username);
    }

}
