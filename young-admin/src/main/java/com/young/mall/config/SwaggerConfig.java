package com.young.mall.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.young.mall.domain.SwaggerApiInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: swagger配置类
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:57
 */
@Profile("dev")
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public void swaggerApiInfo(SwaggerApiInfo swaggerApiInfo) {
        swaggerApiInfo.setApiBasePackage("com.young.mall.controller");
        swaggerApiInfo.setTitle("young后台管理系统");
        swaggerApiInfo.setDescription("young后台相关接口");
        swaggerApiInfo.setContactName("yqz");
        swaggerApiInfo.setVersion("1.0");
        swaggerApiInfo.setGroup("young后台接口");
        swaggerApiInfo.setEnableSecurity(true);
    }
}
