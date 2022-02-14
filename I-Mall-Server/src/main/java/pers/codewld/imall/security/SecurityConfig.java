package pers.codewld.imall.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.codewld.imall.model.enums.ResultCode;
import pers.codewld.imall.model.vo.ResultVO;
import pers.codewld.imall.service.UmsAdminService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * Spring Security 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-06
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UmsAdminService umsAdminService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 该语句的作用是：去除默认生成的密码
        auth.inMemoryAuthentication();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 访问控制
        http
                .authorizeRequests()
                // 放行登录
                .antMatchers("/**/login/**").permitAll()
                // 放行swagger
                .antMatchers("/**/swagger-ui/**").permitAll()
                .antMatchers("/**/swagger-resources/**").permitAll()
                .antMatchers("/**/api-docs/**").permitAll()
                .anyRequest().authenticated();

        // 未登录异常
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> returnResult(response, new ResultVO(ResultCode.UNAUTHORIZED)));

        // 未授权异常
        http
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> returnResult(response, new ResultVO(ResultCode.FORBIDDEN)));

        // 禁用跨站请求保护
        http
                .csrf().disable();

        // 开启跨域
        http
                .cors();

        // 关闭session
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 禁用默认的登录
        http
                .formLogin()
                .disable();

        // 禁用默认的登出
        http
                .logout()
                .disable();

        // 登录状态读取器
        http
                .addFilterBefore(new JWTVerifyFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 向前端返回结果
     */
    private void returnResult(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(resultVO));
    }
}
