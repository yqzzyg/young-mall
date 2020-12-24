package com.young.mall.service;


import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungAdmin;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:12
 */
public interface AdminService {

    /**
     * 根据用户名查找用户
     * @return
     */
    Optional<YoungAdmin> findAdminByName(String username);

    /**
     * 通过id查询admin
     * @param id
     * @return
     */
    YoungAdmin findAdminById(Integer id);
}
