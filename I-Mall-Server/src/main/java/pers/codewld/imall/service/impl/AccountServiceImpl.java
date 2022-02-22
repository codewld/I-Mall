package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.param.LoginParam;
import pers.codewld.imall.security.JWTUtil;
import pers.codewld.imall.service.AccountService;
import pers.codewld.imall.service.UmsAdminService;

import java.time.LocalDateTime;

/**
 * <p>
 * 账户 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-02-10
 */
@Service
public class AccountServiceImpl implements AccountService {

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
            throw new BadCredentialsException("账号密码错误");
        }
        if (!umsAdmin.getStatus()) {
            throw new DisabledException("账号被禁用");
        }
        // 保存登录记录
        UpdateWrapper<UmsAdmin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", umsAdmin.getId()).set("login_time", LocalDateTime.now());
        boolean res = umsAdminService.update(updateWrapper);
        if (!res) {
            throw new RuntimeException("登录记录保存失败");
        }
        return jwtUtil.sign(umsAdmin);
    }

}
