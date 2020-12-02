package com.young.mall.service;

import com.young.db.entity.YoungGoodsAttribute;

import java.util.List;

/**
 * @Description: 客户端商品属性
 * @Author: yqz
 * @CreateDate: 2020/12/2 21:10
 */
public interface ClientGoodsAttributeService {

    /**
     * 通过商品id查找商品属性
     *
     * @param goodsId
     * @return
     */
    List<YoungGoodsAttribute> queryByGid(Integer goodsId);
}
