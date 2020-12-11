package com.young.mall.service;

import com.young.db.entity.YoungUserAccount;

/**
 * @Description: 账户业务
 * @Author: yqz
 * @CreateDate: 2020/12/11 11:00
 */
public interface MallAccountService {

    /**
     * 查询用户账号的总金额和剩余金额
     *
     * @param shareUserId 用户id
     * @return 返回用户对象
     */
    YoungUserAccount findShareUserAccountByUserId(Integer shareUserId);
}
