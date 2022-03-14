package pers.codewld.imall.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.codewld.imall.admin.model.param.LoginParam;
import pers.codewld.imall.admin.model.vo.RouterVO;
import pers.codewld.imall.admin.service.AccountService;

import java.util.List;

/**
 * <p>
 * 账户 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public String login(@RequestBody @Validated LoginParam loginParam) {
        return accountService.login(loginParam);
    }

    @GetMapping("/router")
    @ApiOperation("获取前端路由")
    public List<RouterVO> router() {
        return accountService.router();
    }
}
