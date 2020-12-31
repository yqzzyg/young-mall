package com.young.mall.service;

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
     * @param id
     * @param goodsId
     * @param num
     * @return
     */
    Integer reduceStock(Integer id, Integer goodsId, Short num);

    /**
     * 增加库存
     *
     * @param id
     * @param num
     * @return
     */
    int addStock(Integer id, Short num);
}
