package com.young.mall.service;


import com.young.db.entity.YoungOrderGoods;

import java.util.List;

/**
 * @Description: 订单商品业务
 * @Author: yqz
 * @CreateDate: 2020/12/12 23:03
 */
public interface ClientOrderGoodsService {

    /**
     * 通过订单id查询订单商品
     *
     * @param orderId 订单id
     * @return 订单商品list
     */
    List<YoungOrderGoods> queryByOid(Integer orderId);

    /**
     * 根据订单和商品id，获取订单商品列表
     *
     * @param orderId 订单id
     * @param goodsId 商品id
     * @return
     */
    List<YoungOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId);

    /**
     * 通过主键id查询订单商品
     *
     * @param id
     * @return
     */
    YoungOrderGoods findById(Integer id);

    /**
     * 根据订单id更新订单商品表
     *
     * @param orderGoods
     * @return
     */
    Integer updateById(YoungOrderGoods orderGoods);

    /**
     * 添加订单商品
     * @param orderGoods
     * @return
     */
    Integer add(YoungOrderGoods orderGoods);
}
