package com.young.mall.service;

import java.util.Optional;
import java.util.Set;

/**
 * @Description: 权限相关service
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:36
 */
public interface PermissionService {

    Optional<Set<String>> queryByRoleIds(Integer[] roleIds);

}
