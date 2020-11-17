package com.young.mall.service;

import com.young.db.entity.YoungRole;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 角色管理
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:36
 */
public interface AdminRoleService {
    /**
     * 查询所有角色
     *
     * @return
     */
    Optional<List<YoungRole>> listAll();
}
