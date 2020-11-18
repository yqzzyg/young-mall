package com.young.mall.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 用户统计
 * @Author: yqz
 * @CreateDate: 2020/11/18 21:51
 */
public interface AdminStatService {
    /**
     * 用户统计
     *
     * @return
     */
    Optional<List<Map>> statUser();

    /**
     * 订单统计
     *
     * @return
     */
    Optional<List<Map>> statOrder();

    /**
     * 商品统计
     *
     * @return
     */
    Optional<List<Map>> statGoods();
}
