package com.young.mall.service;

import com.young.db.entity.YoungPermission;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 管理员权限
 * @Author: yqz
 * @CreateDate: 2020/11/19 17:51
 */
public interface AdminPermissionsService {

    /**
     * 根据roleId查询该roleId对应的所有权限
     *
     * @param roleId
     * @return
     */
    Optional<List<YoungPermission>> getPermissions(Integer roleId);

    /**
     * 根据roleId查询该roleId对应的所有权限具体的值
     *
     * @param roleId
     * @return
     */
    List<String> getPermissionsList(Integer roleId);
}
