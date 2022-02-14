package pers.codewld.imall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Web 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-14
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 统一响应的contentType
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}
