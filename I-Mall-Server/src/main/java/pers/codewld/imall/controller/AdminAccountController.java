package pers.codewld.imall.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.imall.model.param.LoginParam;
import pers.codewld.imall.service.AdminAccountService;

/**
 * <p>
 * 后台账户 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/admin")
public class AdminAccountController {

    @Autowired
    AdminAccountService adminAccountService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public String login(@RequestBody LoginParam loginParam) {
        return adminAccountService.login(loginParam);
    }

}
