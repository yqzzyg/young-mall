package com.young.mall.service;

import com.young.db.entity.YoungGroupon;

import java.util.List;

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

    /**
     * 获取用户发起的团购记录
     *
     * @param userId 用户id
     * @return
     */
    List<YoungGroupon> queryMyGroupon(Integer userId);

    /**
     * 获取用户参与的团购记录
     *
     * @param userId 用户id
     * @return
     */
    List<YoungGroupon> queryMyJoinGroupon(Integer userId);

    /**
     * 返回某个发起的团购参与人数
     *
     * @param grouponId
     * @return
     */
    Integer countGroupon(Integer grouponId);
}
