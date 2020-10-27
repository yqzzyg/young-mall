package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungAdmin;
import com.young.mall.domain.AdminUser;
import com.young.mall.dto.AdminLoginParam;
import com.young.mall.common.ResBean;
import com.young.mall.service.AuthService;
import com.young.mall.service.PermissionService;
import com.young.mall.service.YoungAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private YoungAdminService adminService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResBean login(@RequestBody AdminLoginParam loginParam) {
        logger.info("用户登录入参：{}", loginParam);
        String token = authService.login(loginParam.getUsername(), loginParam.getPassword());
        Map<String, String> map = new HashMap<>();

        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return ResBean.success(map);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/info")
    public ResBean getLoginInfo(Principal principal) {

        if (BeanUtil.isEmpty(principal)) {
            return ResBean.unauthorized(null);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<YoungAdmin> adminByName = adminService.findAdminByName(principal.getName());
        if (!adminByName.isPresent()) {
            ResBean.unauthorized("无此用户");
        }
        YoungAdmin admin = adminByName.get();
        AdminUser adminUser = new AdminUser();
        BeanUtil.copyProperties(admin, adminUser);
        Map<String, Object> data = new HashMap<>();
        data.put("name", adminUser.getUsername());
        data.put("avatar", adminUser.getAvatar());

        Integer[] roleIds = adminUser.getRoleIds();
        Optional<Set<String>> optionalSet = permissionService.queryByRoleIds(adminUser.getRoleIds());

//        Set<String> roles = roleService.queryByIds(roleIds);

//        data.put("roles", roles);
        data.put("perms", optionalSet.get());
        logger.info("【请求结束】系统管理->用户信息获取,响应结果:{}", JSONUtil.toJsonStr(data));

        return ResBean.success(data);
    }
}
