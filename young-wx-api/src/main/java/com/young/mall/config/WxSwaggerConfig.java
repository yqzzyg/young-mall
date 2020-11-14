package com.young.mall.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.young.mall.domain.SwaggerApiInfo;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/14 20:12
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class WxSwaggerConfig extends BaseSwaggerConfig {
    @Override
    public void swaggerApiInfo(SwaggerApiInfo swaggerApiInfo) {
        swaggerApiInfo.setApiBasePackage("com.young.mall.controller");
        swaggerApiInfo.setTitle("young-mall微信小程序接口");
        swaggerApiInfo.setDescription("young-mall微信小程序相关接口");
        swaggerApiInfo.setContactName("yqz");
        swaggerApiInfo.setVersion("1.0");
        swaggerApiInfo.setGroup("young小程序接口");
        swaggerApiInfo.setEnableSecurity(true);
    }
}
