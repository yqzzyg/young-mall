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
}
