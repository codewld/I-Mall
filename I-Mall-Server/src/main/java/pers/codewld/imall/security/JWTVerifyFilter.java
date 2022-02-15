package pers.codewld.imall.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pers.codewld.imall.util.BeanUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        if (!req.getServletPath().contains("login") && jwtToken != null && !jwtUtil.isInvalid(jwtToken)) {
            MyUserDetails user = jwtUtil.decode(jwtToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
