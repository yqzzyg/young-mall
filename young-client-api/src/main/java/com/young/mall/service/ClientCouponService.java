package com.young.mall.service;

import com.young.db.entity.YoungCoupon;
import com.young.mall.common.ResBean;

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

    /**
     * 根据用户信息查询可用的优惠券
     *
     * @param userId 用户id
     * @param offset 起始页
     * @param limit  每页大小
     * @return
     */
    List<YoungCoupon> queryAvailableList(Integer userId, int offset, int limit);

    /**
     * 根据优惠券id查询优惠券
     *
     * @param id 优惠券id
     * @return
     */
    YoungCoupon findById(Integer id);


    /**
     * 根据用户id，一键获取优惠券
     *
     * @param userId
     * @return
     */
    ResBean receiveAll(Integer userId);

    /**
     * 查询用户的优惠券
     *
     * @param userId 用户id
     * @return 优惠券个数
     */
    int queryUserCouponCnt(Integer userId);
}
