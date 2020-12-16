package com.young.mall.service;

import com.young.db.entity.YoungCouponUser;

import java.util.List;

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

    /**
     * 个人优惠券列表
     *
     * @param userId   用户id
     * @param couponId 优惠券id
     * @param status   状态
     * @param page     分页
     * @param size     分页大小
     * @param sort     排序依据
     * @param order    排序方式
     * @return
     */
    List<YoungCouponUser> queryList(Integer userId, Integer couponId,
                                    Short status,
                                    Integer page, Integer size,
                                    String sort, String order);

    /**
     * 查询一个
     *
     * @param userId
     * @param couponId
     * @return
     */
    YoungCouponUser queryOne(Integer userId, Integer couponId);
}
