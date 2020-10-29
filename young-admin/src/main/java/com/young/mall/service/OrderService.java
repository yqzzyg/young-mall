package com.young.mall.service;

import java.util.Optional;

/**
 * @Description: 订单service
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:52
 */
public interface OrderService {

    /**
     * 订单数量
     * @return
     */
    Optional<Integer> count(Integer userId);

    /**
     * 总订单数
     * @return
     */
    Optional<Integer> count();
}
