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

    /**
     * 分页查询角色
     *
     * @return
     */
    Optional<List<YoungRole>> list(String roleName,
                                   Integer page, Integer size,
                                   String sort, String order);

    /**
     * 根据ID查询角色对象
     *
     * @param rid
     * @return
     */
    Optional<YoungRole> findById(Integer rid);

    /**
     * 创建角色
     *
     * @param youngRole
     * @return
     */
    Optional<Integer> create(YoungRole youngRole);

    /**
     * 更新角色
     *
     * @param youngRole
     * @return
     */
    Optional<Integer> updateById(YoungRole youngRole);

    /**
     * 删除
     *
     * @param rid
     * @return
     */
    Optional<Integer> deleteById(Integer rid);

    /**
     * 校验是否已经存在某个角色
     *
     * @param roleName
     * @return
     */
    boolean checkExist(String roleName);
}
