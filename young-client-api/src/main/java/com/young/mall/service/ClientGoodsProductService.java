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
}
