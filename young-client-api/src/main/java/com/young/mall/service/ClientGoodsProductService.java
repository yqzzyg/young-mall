package com.young.mall.service;

import com.young.db.entity.YoungCart;
import com.young.db.entity.YoungGoodsProduct;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:53
 */
public interface ClientGoodsProductService {

    /**
     * 根据商品货品表的货品id，查询商品货品
     *
     * @param productId
     * @return
     */
    YoungGoodsProduct findById(Integer productId);

    /**
     * 减库存
     *
     * @param checkGoods 购物车单个商品，包含young_goods主键id,young_goods_product主键id
     * @return
     */
    Integer reduceStock(YoungCart checkGoods);

    /**
     * 增加库存
     *
     * @param id
     * @param num
     * @return
     */
    int addStock(Integer id, Short num);
}
