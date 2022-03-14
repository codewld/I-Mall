package pers.codewld.imall.admin.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Swagger 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@Configuration
@EnableOpenApi
@EnableKnife4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.codewld.imall.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("I-Mall")
                .description("I-Mall接口文档")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        list.add(apiKey);
        return list;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .operationSelector(o -> o.requestMappingPattern().matches("^(?!.*login).*$"))
                .build());
        return list;
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        list.add(new SecurityReference("Authorization", authorizationScopes));
        return list;
    }
}
