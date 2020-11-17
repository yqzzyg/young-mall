package com.young.mall.service;

import com.young.db.entity.YoungAdmin;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 管理员service
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:12
 */
public interface AdminUserService {
    /**
     * 分页查询后台用户
     *
     * @param username
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungAdmin>> list(String username, Integer page, Integer size, String sort, String order);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    Optional<List<YoungAdmin>> findAdminByName(String username);

    /**
     * 添加后台用户
     *
     * @param youngAdmin
     * @return
     */
    Optional<Integer> create(YoungAdmin youngAdmin);

    /**
     * 根据id查询后台用户
     *
     * @param id
     * @return
     */
    Optional<YoungAdmin> findById(Integer id);

    /**
     * 根据用户id更新
     *
     * @param youngAdmin
     * @return
     */
    Optional<Integer> updateById(YoungAdmin youngAdmin);

    /**
     * 根据 id 删除
     *
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
