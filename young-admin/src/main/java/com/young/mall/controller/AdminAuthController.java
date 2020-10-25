package com.young.mall.controller;

import com.young.mall.dto.AdminLoginParam;
import com.young.mall.common.ResponseBean;
import com.young.mall.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 后台用户管理
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:59
 */
@Api(tags = "AdminAuthController", description = "后台用户管理")
@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AuthService authService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody AdminLoginParam loginParam) {
        logger.info("用户登录入参：{}", loginParam);
        String token = authService.login(loginParam.getUsername(), loginParam.getPassword());
        Map<String, String> map = new HashMap<>();

        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return ResponseBean.success(map);
    }
}
