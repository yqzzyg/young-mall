package com.young.mall.service;

import com.young.db.entity.YoungCouponUser;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/15 21:32
 */
public interface MallCouponUserService {

    /**
     * 分页查询用户
     * @param userId
     * @param couponId
     * @param status
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungCouponUser>> list(Integer userId, Integer couponId,
                                         Short status,
                                         Integer page, Integer size,
                                         String sort, String order);
}
