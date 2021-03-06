package com.young.mall.config;

import com.young.mall.service.impl.ClientUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description: 安全配置
 * @Author: yqz
 * @CreateDate: 2020/11/21 22:02
 */
@Configuration
@EnableWebSecurity
public class ClientSecurityConfig extends BaseSecurityConfig {

    @Autowired
    private ClientUserDetailsServiceImpl detailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> detailsService.loadUserByUsername(username);
    }

}
