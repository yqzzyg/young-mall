package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungGoodsProduct;
import com.young.mall.service.ClientGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 商品货品业务
 * @Author: yqz
 * @CreateDate: 2020/12/5 17:27
 */
@Service
public class ClientGoodsProductServiceImpl implements ClientGoodsProductService {


    @Autowired
    private YoungGoodsProductMapper youngGoodsProductMapper;

    @Override
    public YoungGoodsProduct findById(Integer productId) {
        YoungGoodsProduct goodsProduct = youngGoodsProductMapper.selectByPrimaryKey(productId);
        return goodsProduct;
    }
}
