package com.young.db.mapper;

import com.young.db.pojo.RolePermissionPojo;

import java.util.List;

/**
 * @Description: 权限嵌套查询
 * @Author: yqz
 * @CreateDate: 2020/11/20 11:56
 */
public interface RolePermissionMapper {

    /**
     * 查询权限的嵌套关系
     *
     * @return
     */
    List<RolePermissionPojo> listRolePermission();
}
