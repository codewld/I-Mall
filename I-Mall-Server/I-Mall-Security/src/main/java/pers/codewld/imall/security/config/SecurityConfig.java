package pers.codewld.imall.security.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import pers.codewld.imall.common.model.enums.ResultCode;
import pers.codewld.imall.common.model.vo.ResultVO;

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
public abstract class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * JWT登陆状态读取器
     */
    protected abstract GenericFilterBean JWTVerifyFilter();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
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
                .antMatchers("/**/doc.html").permitAll()
                .anyRequest().authenticated();

        // 未登录异常
        http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) ->
                                returnResult(response, ResultVO.error(ResultCode.UNAUTHORIZED))
                );

        // 未授权异常
        http
                .exceptionHandling()
                .accessDeniedHandler(
                        (request, response, accessDeniedException) ->
                                returnResult(response, ResultVO.error(ResultCode.FORBIDDEN)));

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
                .addFilterBefore(JWTVerifyFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/**/*.js").antMatchers("/**/*.css");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 用于禁止生成临时用户
        return super.authenticationManagerBean();
    }

    /**
     * 向前端返回结果
     */
    private void returnResult(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        writer.println(mapper.writeValueAsString(resultVO));
    }
}