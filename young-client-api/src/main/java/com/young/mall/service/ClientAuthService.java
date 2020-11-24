package com.young.mall.service;

import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 登录注册相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/24 10:22
 */
public interface ClientAuthService {

    /**
     * 用户注册
     *
     * @param registerDto
     * @return
     */
    ResBean register(ClientUserDto registerDto, HttpServletRequest request);

}
