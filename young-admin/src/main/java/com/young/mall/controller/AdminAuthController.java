package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.young.mall.common.ResBean;
import com.young.mall.domain.AdminUser;
import com.young.mall.domain.dto.AdminLoginParam;
import com.young.mall.service.AuthService;
import com.young.mall.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    private RoleService roleService;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResBean login(@Valid @RequestBody AdminLoginParam loginParam) {
        logger.error("用户登录入参：{}", loginParam);
        String token = authService.login(loginParam.getUsername(), loginParam.getPassword());
        Map<String, String> map = new HashMap<>();

        map.put("token", token);
        map.put("tokenHead", tokenHead);
        return ResBean.success(map);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/info")
    public ResBean getLoginInfo() {


        AdminUser adminUser = authService.getUserInfo();
        if (BeanUtil.isEmpty(adminUser)) {
            return ResBean.failed("当前用户未登录");
        }
        Collection<? extends GrantedAuthority> authorities = adminUser.getAuthorities();

        List<String> list = new ArrayList<>();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        while (iterator.hasNext()) {
            String perm = iterator.next().toString();
            list.add(perm);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", adminUser.getUsername());
        data.put("avatar", adminUser.getAvatar());
        Integer[] roleIds = adminUser.getRoleIds();

        Optional<Set<String>> optionalSet = roleService.getRolesByIds(roleIds);

        if (!optionalSet.isPresent()) {
            return ResBean.failed("权限错误");
        }

        data.put("roles", optionalSet.get());
        data.put("perms", list);
        logger.error("【请求结束】系统管理->用户信息获取,响应结果:{}", JSONUtil.toJsonStr(data));

        return ResBean.success(data);
    }
}
