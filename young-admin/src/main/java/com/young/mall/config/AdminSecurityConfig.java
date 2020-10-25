package com.young.mall.config;

import com.young.mall.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description: security相关配置
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:49
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends BaseSecurityConfig{

    @Autowired
    private UserDetailsServiceImpl detailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> detailsService.loadUserByUsername(username);
    }
}
