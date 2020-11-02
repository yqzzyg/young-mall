package com.young.mall.service;

import com.young.mall.domain.AdminUser;

/**
 * @Description: 登录相关service
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:02
 */
public interface AuthService {
    String login(String username, String password);

    /**
     * 获取用户信息
     * @return
     */
    AdminUser getUserInfo();
}
