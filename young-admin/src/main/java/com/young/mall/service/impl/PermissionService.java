package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;

import java.util.Iterator;

/**
 * @Description: 接口权限判断工具
 * @Author: yqz
 * @CreateDate: 2020/11/18 22:37
 */
@Service("pms")
public class PermissionService {
    /**
     * 判断接口是否有xxx:xxx权限
     *
     * @param permission 权限
     * @return {boolean}
     */
    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        final Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        while (iterator.hasNext()) {
            String per = iterator.next().toString();
            if (per.equals("*")) {
                return true;
            }
            if (PatternMatchUtils.simpleMatch(permission, per)) {
                return true;
            }
        }
        return false;
        /*return authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));*/
    }
}
