package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDto;
import com.young.mall.service.ClientAuthService;
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
import java.util.Map;

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
    private ClientAuthService clientAuthService;

    @ApiOperation("注册")
    @PostMapping("register")
    public ResBean<Map<Object, Object>> register(@Valid @RequestBody ClientUserDto clientUserDto, HttpServletRequest request) {
        logger.info("客户端注册入参：{}", clientUserDto);

        return clientAuthService.register(clientUserDto, request);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResBean login() {
        return ResBean.success("成功");
    }
}
