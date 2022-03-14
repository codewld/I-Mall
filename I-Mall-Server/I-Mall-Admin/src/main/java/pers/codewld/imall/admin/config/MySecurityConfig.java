package pers.codewld.imall.admin.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;
import pers.codewld.imall.admin.service.UmsAdminService;
import pers.codewld.imall.admin.service.UmsRoleService;
import pers.codewld.imall.common.exception.CustomException;
import pers.codewld.imall.common.model.enums.ResultCode;
import pers.codewld.imall.common.util.BeanUtil;
import pers.codewld.imall.security.config.SecurityConfig;
import pers.codewld.imall.security.model.MyUserDetails;
import pers.codewld.imall.security.util.JWTUtil;

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
 * Spring Security 配置实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-14
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class MySecurityConfig extends SecurityConfig {

    @Override
    protected GenericFilterBean JWTVerifyFilter() {
        return new GenericFilterBean() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;
                String jwtToken = req.getHeader("authorization");
                if (req.getServletPath().contains("login")) {
                    chain.doFilter(request, response);
                    return;
                }
                // 若没有JWT
                if (jwtToken == null) {
                    chain.doFilter(request, response);
                    return;
                }
                // 若JWT不合法
                if (jwtUtil().isIllegal(jwtToken)) {
                    forward2Error(request, response, ResultCode.UNAUTHORIZED);
                    return;
                }
                // 判断JWT是否能正确解密
                MyUserDetails user = jwtUtil().decode(jwtToken);
                if (user == null) {
                    forward2Error(request, response, ResultCode.TOKEN_WRONG);
                    return;
                }
                // 判断JWT是否失效
                if (isInvalidated(user, jwtToken)) {
                    forward2Error(request, response, ResultCode.TOKEN_INVALIDATED);
                    return;
                }
                // 判断用户是否被禁用
                if (isDisabled(user)) {
                    forward2Error(request, response, ResultCode.DISABLED);
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
                return umsAdminService().listDisabled().contains(user.getId());
            }

            /**
             * 判断JWT是否失效
             */
            boolean isInvalidated(MyUserDetails user, String jwtToken) {
                Long id = user.getId();
                Long issueTime = jwtUtil().getIssueTime(jwtToken);
                return umsAdminService().isJWTInvalidated(id, issueTime);
            }

            /**
             * 获取用户拥有的角色对应的权限
             */
            List<SimpleGrantedAuthority> getAuthorities(MyUserDetails user) {
                // 获取所有角色ID
                List<Long> roleIdList = user.getRoleIdList();
                if (CollectionUtils.isEmpty(roleIdList)) {
                    return null;
                }
                // 获取所有菜单编码
                List<String> menuCodeList = new ArrayList<>();
                for (Long roleId : roleIdList) {
                    menuCodeList.addAll(umsRoleService().listMenuCode(roleId));
                }
                menuCodeList = menuCodeList.stream().distinct().collect(Collectors.toList());
                // 将菜单编码列表转换为Authority列表
                return menuCodeList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }

            /**
             * 跳转至错误控制器
             */
            void forward2Error(ServletRequest request, ServletResponse response, ResultCode resultCode) throws ServletException, IOException {
                request.setAttribute("exception", new CustomException(resultCode));
                request.getRequestDispatcher("/err").forward(request, response);
            }

            JWTUtil jwtUtil() {
                return BeanUtil.getBean(JWTUtil.class);
            }

            UmsAdminService umsAdminService() {
                return BeanUtil.getBean(UmsAdminService.class);
            }

            UmsRoleService umsRoleService() {
                return BeanUtil.getBean(UmsRoleService.class);
            }
        };
    }

}
