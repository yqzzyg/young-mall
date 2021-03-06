package com.young.mall.config;

import com.young.mall.config.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/5 10:13
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler是指你想在url请求的路径
        //addResourceLocations是图片存放的真实路径
        registry.addResourceHandler(storageProperties.getLocal().getAddress() + "**")
                .addResourceLocations("file:" + storageProperties.getLocal().getStoragePath());
    }
}

