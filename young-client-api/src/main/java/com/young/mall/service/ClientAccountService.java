package com.young.mall.service;

import com.young.db.entity.YoungUserAccount;

/**
 * @Description: 用户账户业务
 * @Author: yqz
 * @CreateDate: 2020/12/22 12:14
 */
public interface ClientAccountService {

    /**
     * 根据用户id，查询用户账号的总金额和剩余金额
     * @param shareUserId
     * @return
     */
    YoungUserAccount findShareUserAccountByUserId(Integer shareUserId);
}
