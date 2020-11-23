package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.domain.RegisterDto;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description: 用户端登录、注册相关
 * @Author: yqz
 * @CreateDate: 2020/11/23 15:40
 */
@Api(tags = "ClientAuthController", description = "用户端登录")
@RestController
@RequestMapping("/wx/auth")
public class ClientAuthController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @ApiOperation("注册")
    @PostMapping("register")
    public ResBean register(@Valid @RequestBody RegisterDto registerDto, HttpServletRequest request) {

        ResBean result = clientUserService.register(registerDto, request);
        return result;
    }
}
