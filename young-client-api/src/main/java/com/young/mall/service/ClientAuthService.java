package com.young.mall.service;

import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientLoginDto;
import com.young.mall.domain.ClientUserDto;
import com.young.mall.domain.WxLoginInfo;
import me.chanjar.weixin.common.error.WxErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    ResBean<Map<String, Object>> register(ClientUserDto registerDto, HttpServletRequest request);

    /**
     * 用户登录
     *
     * @param clientLoginDto
     * @param request
     * @return
     */
    ResBean<Map<String, Object>> login(ClientLoginDto clientLoginDto, HttpServletRequest request);

    /**
     * 通过微信登录
     *
     * @param wxLoginInfo
     * @param request
     * @return
     */
    ResBean loginByWeixin(WxLoginInfo wxLoginInfo, HttpServletRequest request) throws WxErrorException;

}
