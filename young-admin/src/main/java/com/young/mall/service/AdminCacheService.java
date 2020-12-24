package com.young.mall.service;

import com.young.db.entity.YoungAdmin;

import java.util.Set;

/**
 * @Description: 后台用户缓存操作类
 * @Author: yqz
 * @CreateDate: 2020/12/24 17:36
 */
public interface AdminCacheService {

    /**
     * 删除后台用户缓存
     *
     * @param adminId
     */
    void delAdmin(Integer adminId);


    /**
     * 获取缓存后台用户信息
     *
     * @param username
     */
    YoungAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     *
     * @param admin
     */
    void setAdmin(YoungAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     *
     * @param username
     * @return
     */
    Set<String> getPermissionsList(String username);

    /**
     * 设置后台后台用户资源列表
     *
     * @param username
     * @param permissionsList
     */
    void setResourceList(String username, Set<String> permissionsList);
}
