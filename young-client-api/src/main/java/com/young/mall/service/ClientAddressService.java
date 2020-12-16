package com.young.mall.service;

import com.young.db.entity.YoungAddress;

/**
 * @Description: 收获地址service
 * @Author: yqz
 * @CreateDate: 2020/12/16 14:47
 */
public interface ClientAddressService {

    /**
     * 查询默认收获地址
     *
     * @param userId 用户id
     * @return
     */
    YoungAddress findDefault(Integer userId);

    /**
     * 通过id查询收货地址
     *
     * @param id
     * @return
     */
    YoungAddress findById(Integer id);

}
