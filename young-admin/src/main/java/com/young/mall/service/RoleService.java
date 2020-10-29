package com.young.mall.service;


import java.util.Optional;
import java.util.Set;

/**
 * @Description: 操作角色service
 * @Author: yqz
 * @CreateDate: 2020/10/29 12:09
 */
public interface RoleService {

    /**
     * 根据 id 获取角色
     * @return
     */
    Optional<Set<String>> getRolesByIds(Integer[] ids);
}
