package com.young.mall.service;

import com.young.db.entity.YoungCoupon;

import java.math.BigDecimal;

/**
 * @Description: 检测优惠券
 * @Author: yqz
 * @CreateDate: 2020/12/16 16:32
 */
public interface CouponVerifyService {

    /**
     * 检测优惠券是否适合
     *
     * @param userId            用户id
     * @param couponId          优惠券id
     * @param checkedGoodsPrice 商品总价
     * @return
     */
    YoungCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice);
}
