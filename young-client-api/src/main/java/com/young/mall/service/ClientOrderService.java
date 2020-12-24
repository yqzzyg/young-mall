package com.young.mall.service;

import com.young.db.entity.YoungOrder;
import com.young.db.entity.YoungOrderGoods;
import com.young.mall.common.ResBean;
import com.young.mall.domain.vo.OrderCommentVo;

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

    /**
     * 根据订单id查询订单
     *
     * @param orderId 订单id
     * @return
     */
    YoungOrder findById(Integer orderId);

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return
     */
    Map<String, Object> detail(Integer userId, Integer orderId);


    /**
     * 订单物流跟踪
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return
     */
    Map<String, Object> expressTrace(Integer userId, Integer orderId);

    /**
     * 待评价订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     */
    YoungOrderGoods getGoodsByIds(Integer userId, Integer orderId, Integer goodsId);

    /**
     * 添加评论
     *
     * @param commentVo
     * @return
     */
    ResBean comment(OrderCommentVo commentVo);

    /**
     * 更新订单中未评价的订单商品可评价数量
     * @param order
     * @return
     */
    Integer updateWithOptimisticLocker(YoungOrder order);
}
