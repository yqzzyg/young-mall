package com.young.mall.config;

import com.young.mall.domain.SwaggerApiInfo;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Swagger基础配置
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:06
 */
public abstract class BaseSwaggerConfig {
    @Bean
    public Docket createAdminRestApi() {
        //不同Module实现不同的配置以及分组
        SwaggerApiInfo swaggerApiInfo = SwaggerApiInfo.builder().build();
        swaggerApiInfo(swaggerApiInfo);
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swaggerApiInfo))
                //分组名称
                .groupName(swaggerApiInfo.getGroup())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerApiInfo.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build();
        if (swaggerApiInfo.isEnableSecurity()) {
            docket.securitySchemes(securitySchemes()).securityContexts(securityContexts());
        }
        return docket;
    }

    private ApiInfo apiInfo(SwaggerApiInfo swaggerApiInfo) {
        return new ApiInfoBuilder()
                .title(swaggerApiInfo.getTitle())
                .description(swaggerApiInfo.getDescription())
                .contact(new Contact(swaggerApiInfo.getContactName(), swaggerApiInfo.getContactUrl(), swaggerApiInfo.getContactEmail()))
                .version(swaggerApiInfo.getVersion())
                .build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/*/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }

    /**
     * 自定义Swagger配置
     */
    public void swaggerApiInfo(SwaggerApiInfo swaggerApiInfo) {

    };
}
