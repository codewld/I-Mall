package pers.codewld.imall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.codewld.imall.entity.UmsAdmin;
import pers.codewld.imall.param.LoginParam;
import pers.codewld.imall.service.AdminAccountService;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.security.JWTUtil;

/**
 * <p>
 * 后台账户 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    @Autowired
    UmsAdminService umsAdminService;

    @Autowired
    PasswordEncoder MD5PasswordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public String login(LoginParam loginParam) {
        UmsAdmin umsAdmin = umsAdminService.getByUsername(loginParam.getUsername());
        if (umsAdmin == null || !MD5PasswordEncoder.matches(loginParam.getPassword(), umsAdmin.getPassword())) {
            return "账号密码错误";
        }
        return jwtUtil.sign(umsAdmin);
    }

}
