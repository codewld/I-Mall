package pers.codewld.imall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.codewld.imall.model.entity.UmsAdmin;
import pers.codewld.imall.model.entity.UmsMenu;
import pers.codewld.imall.model.param.LoginParam;
import pers.codewld.imall.model.vo.RouterVO;
import pers.codewld.imall.security.JWTUtil;
import pers.codewld.imall.service.AccountService;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.service.UmsMenuService;
import pers.codewld.imall.service.UmsRoleService;
import pers.codewld.imall.util.TransformUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    UmsMenuService umsMenuService;

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
        umsAdmin.setRoleIdList(umsAdminService.listRoleId(umsAdmin.getId()));
        // 保存登录记录
        UpdateWrapper<UmsAdmin> updateWrapper = new UpdateWrapper<UmsAdmin>()
                .eq("id", umsAdmin.getId()).set("login_time", LocalDateTime.now());
        boolean res = umsAdminService.update(updateWrapper);
        if (!res) {
            throw new RuntimeException("登录记录保存失败");
        }
        return jwtUtil.sign(umsAdmin);
    }

    @Override
    public List<RouterVO> router() {
        List<UmsMenu> menuList = new ArrayList<>();
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            // 获取权限对应的菜单对象，添加
            UmsMenu umsMenu = umsMenuService.get(authority.getAuthority());
            menuList.add(umsMenu);
            // 递归添加父结点
            Long parentId = umsMenu.getParentId();
            while (parentId != 0) {
                UmsMenu parentUmsMenu = umsMenuService.get(parentId);
                menuList.add(parentUmsMenu);
                parentId = parentUmsMenu.getParentId();
            }
        }
        // 去重
        menuList = menuList
                .stream()
                .distinct()
                .collect(Collectors.toList());
        List<UmsMenu> menuTree = umsMenuService.generateTree(menuList);
        return menuTree
                .stream()
                .map(TransformUtil::transform2Router)
                .collect(Collectors.toList());
    }

}
