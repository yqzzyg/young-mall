package com.young.mall.service;

import com.young.db.entity.YoungOrder;

import java.util.Optional;

/**
 * @Description: 公共订单service
 * @Author: yqz
 * @CreateDate: 2020/11/2 16:28
 */
public interface CommonOrderService {

    /**
     * 设置订单取消状态
     * @param order
     * @return
     */
    Optional<Integer> updateWithOptimisticLocker(YoungOrder order);
}
