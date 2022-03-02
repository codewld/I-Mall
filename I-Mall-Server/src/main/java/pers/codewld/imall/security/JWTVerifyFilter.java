package pers.codewld.imall.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;
import pers.codewld.imall.service.UmsAdminService;
import pers.codewld.imall.service.UmsRoleService;
import pers.codewld.imall.util.BeanUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 登录状态读取器
 * </p>
 * <p>
 * 描述：过滤请求，当请求中带有JWT时，读取登录状态
 * </p>
 *
 * @author codewld
 * @since 2022-02-11
 */
public class JWTVerifyFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        JWTUtil jwtUtil = BeanUtil.getBean(JWTUtil.class);
        HttpServletRequest req = (HttpServletRequest) request;
        String jwtToken = req.getHeader("authorization");
        if (req.getServletPath().contains("login")) {
            chain.doFilter(request, response);
            return;
        }
        if (jwtUtil.isInvalid(jwtToken)) {
            chain.doFilter(request, response);
            return;
        }
        MyUserDetails user = jwtUtil.decode(jwtToken);
        // 判断用户是否被禁用
        if (isDisabled(user)) {
            chain.doFilter(request, response);
            return;
        }
        // 获取用户拥有的角色对应的权限
        List<SimpleGrantedAuthority> authorities = this.getAuthorities(user);
        // 放置Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 判断该用户是否被禁用
     */
    boolean isDisabled(MyUserDetails user) {
        UmsAdminService umsAdminService = BeanUtil.getBean(UmsAdminService.class);
        return umsAdminService.listDisabled().contains(user.getId());
    }

    /**
     * 获取用户拥有的角色对应的权限
     */
    List<SimpleGrantedAuthority> getAuthorities(MyUserDetails user) {
        UmsRoleService umsRoleService = BeanUtil.getBean(UmsRoleService.class);
        // 获取所有角色ID
        List<Long> roleIdList = user.getRoleIdList();
        if (CollectionUtils.isEmpty(roleIdList)) {
            return null;
        }
        // 获取所有菜单编码
        List<String> menuCodeList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            menuCodeList.addAll(umsRoleService.listMenuCode(roleId));
        }
        menuCodeList = menuCodeList
                .stream()
                .distinct()
                .collect(Collectors.toList());
        // 将菜单编码列表转换为Authority列表
        return menuCodeList
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
