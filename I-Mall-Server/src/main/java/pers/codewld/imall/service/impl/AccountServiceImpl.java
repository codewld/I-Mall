package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.entity.UmsRole;
import pers.codewld.imall.model.param.LoginParam;
import pers.codewld.imall.security.JWTUtil;
import pers.codewld.imall.service.AccountService;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.service.UmsRoleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    UmsRoleService umsRoleService;

    @Autowired
    PasswordEncoder MD5PasswordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public String login(LoginParam loginParam) {
        // 查询基本信息
        UmsAdmin umsAdmin = umsAdminService.getByUsername(loginParam.getUsername());
        // 校验基本信息
        if (umsAdmin == null || !MD5PasswordEncoder.matches(loginParam.getPassword(), umsAdmin.getPassword())) {
            throw new BadCredentialsException("账号密码错误");
        }
        if (!umsAdmin.getStatus()) {
            throw new DisabledException("账号被禁用");
        }
        // 查询用户对应的角色信息
        List<Long> roleIdList = umsAdminService.listRoleId(umsAdmin.getId());
        List<String> roleCodeList = null;
        if (!CollectionUtils.isEmpty(roleIdList)) {
            List<UmsRole> roleList = umsRoleService.listByIds(roleIdList);
            roleCodeList = roleList.stream().map(UmsRole::getCode).collect(Collectors.toList());
        }
        umsAdmin.setRoleCodeList(roleCodeList);
        // 保存登录记录
        UpdateWrapper<UmsAdmin> updateWrapper = new UpdateWrapper<UmsAdmin>()
                .eq("id", umsAdmin.getId()).set("login_time", LocalDateTime.now());
        boolean res = umsAdminService.update(updateWrapper);
        if (!res) {
            throw new RuntimeException("登录记录保存失败");
        }
        return jwtUtil.sign(umsAdmin);
    }

}
