package com.young.mall.service;

import com.young.db.entity.YoungGroupon;

/**
 * @Description: 团购
 * @Author: yqz
 * @CreateDate: 2020/12/12 22:35
 */
public interface ClientGrouponService {

    /**
     * 根据OrderId查询团购记录
     *
     * @param orderId 订单id
     * @return 订单实体类
     */
    YoungGroupon queryByOrderId(Integer orderId);
}
