package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientLoginDto;
import com.young.mall.domain.ClientUserDto;
import com.young.mall.domain.WxLoginInfo;
import com.young.mall.service.ClientAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description: 用户端登录、注册相关
 * @Author: yqz
 * @CreateDate: 2020/11/23 15:40
 */
@Api(tags = "ClientAuthController")
@RestController
@RequestMapping("/client/auth")
public class ClientAuthController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientAuthService clientAuthService;

    @ApiOperation("注册")
    @PostMapping("register")
    public ResBean<Map<String, Object>> register(@Valid @RequestBody ClientUserDto clientUserDto, HttpServletRequest request) {
        logger.info("客户端注册入参：{}", clientUserDto);

        return clientAuthService.register(clientUserDto, request);
    }

    @ApiOperation("账户密码方式登录")
    @PostMapping("/login")
    public ResBean<Map<String, Object>> login(@Valid @RequestBody ClientLoginDto clientLoginDto, HttpServletRequest request) {
        logger.info("客户端登录入参：{}", clientLoginDto);

        return clientAuthService.login(clientLoginDto, request);
    }

    @ApiOperation("通过微信登录")
    @PostMapping("/loginByWeixin")
    public ResBean<Map<String, Object>> loginByWeixin(@Valid @RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        ResBean<Map<String, Object>> resBean = new ResBean<>();
        try {
            resBean = clientAuthService.loginByWeixin(wxLoginInfo, request);
        } catch (WxErrorException e) {
            logger.info("微信登录失败：{}",e.getError());
            e.printStackTrace();
        }
        return resBean;
    }
}