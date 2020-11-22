package com.young.mall.service;

import com.young.db.entity.YoungCoupon;

import java.util.List;

/**
 * @Description: 用户端优惠券
 * @Author: yqz
 * @CreateDate: 2020/11/22 10:50
 */
public interface ClientCouponService {

    /**
     * 查询所有优惠券
     *
     * @param offset
     * @param limit
     * @return
     */
    List<YoungCoupon> queryList(int offset, int limit);

    List<YoungCoupon> queryAvailableList(Integer userId, int offset, int limit);
}
