package com.young.mall.service;

import com.young.db.entity.YoungCoupon;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 优惠券业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 22:38
 */
public interface MallCouponService {

    /**
     * 分页查询优惠券
     *
     * @param name
     * @param type
     * @param status
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungCoupon>> list(String name, Short type,
                                     Short status, Integer page,
                                     Integer size, String sort,
                                     String order);


    /**
     * 创建优惠券
     *
     * @param youngCoupon
     * @return
     */
    Optional<Integer> create(YoungCoupon youngCoupon);

    /**
     * 通过 id 查找优惠券
     *
     * @param id
     * @return
     */
    Optional<YoungCoupon> findById(Integer id);

    /**
     * 通过 id 更新优惠券
     *
     * @param youngCoupon
     * @return
     */
    Optional<Integer> updateById(YoungCoupon youngCoupon);

    /**
     * 通过 id 删除优惠券
     *
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);

    /**
     * 生成优惠券码
     *
     * @return
     */
    String generateCode();

    /**
     * 通过优惠券码查询优惠券
     *
     * @param code
     * @return
     */
    YoungCoupon findByCode(String code);
}
