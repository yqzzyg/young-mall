package com.young.mall.service;

import java.util.Map;

/**
 * @Description: 订单业务
 * @Author: yqz
 * @CreateDate: 2020/12/10 11:33
 */
public interface ClientOrderService {

    /**
     * 订单信息
     *
     * @param userId
     * @return 订单信息
     */
    Map<String, Object> orderInfo(Integer userId);
}
