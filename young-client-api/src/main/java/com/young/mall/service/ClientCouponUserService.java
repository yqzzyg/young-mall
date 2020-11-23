package com.young.mall.service;

import com.young.db.entity.YoungCouponUser;

/**
 * @Description: 优惠券用户使用
 * @Author: yqz
 * @CreateDate: 2020/11/23 21:16
 */
public interface ClientCouponUserService {

    /**
     * 根据用户 uid 和优惠券 cid 查询使用数量
     *
     * @param uid
     * @param cid
     * @return
     */
    Integer countUserAndCoupon(Integer uid, Integer cid);

    /**
     * 添加优惠券用户关系记录
     *
     * @param youngCouponUser
     * @return
     */
    Integer addCouponUser(YoungCouponUser youngCouponUser);
}
