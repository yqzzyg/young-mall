package com.young.mall.service;

import com.young.db.entity.YoungOrder;

import java.util.List;
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

    /**
     * 订单列表
     *
     * @param userId   用户id
     * @param showType 订单状态
     * @param page     分页
     * @param size     页面大小
     * @return
     */
    Map<String, Object> list(Integer userId, Integer showType, Integer page, Integer size);


    /**
     * 通过订单状态查询订单列表
     *
     * @param userId      用户id
     * @param orderStatus 订单状态
     * @param page        分页
     * @param size        大小
     * @return
     */
    List<YoungOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer size);
}
