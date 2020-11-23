package com.young.mall.service;

/**
 * @Description: 优惠券
 * @Author: yqz
 * @CreateDate: 2020/11/23 21:03
 */
public interface ClientCouponAssignService {

    /**
     * 给新注册用户分发优惠券
     *
     * @param userId
     */
    void assignForRegister(Integer userId);
}
