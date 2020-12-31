package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungGoodsProduct;
import com.young.db.mapper.GoodsProductMapper;
import com.young.mall.service.ClientGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 商品货品业务
 * @Author: yqz
 * @CreateDate: 2020/12/5 17:27
 */
@Service
public class ClientGoodsProductServiceImpl implements ClientGoodsProductService {


    @Autowired
    private YoungGoodsProductMapper youngGoodsProductMapper;

    @Resource
    private GoodsProductMapper goodsProductMapper;

    @Override
    public YoungGoodsProduct findById(Integer productId) {
        YoungGoodsProduct goodsProduct = youngGoodsProductMapper.selectByPrimaryKey(productId);
        return goodsProduct;
    }

    @Override
    public Integer reduceStock(Integer id, Integer goodsId, Short num) {

        // 每次需将商品的销售量加下
        goodsProductMapper.addStock(id, num);
        int count = goodsProductMapper.reduceStock(id, num);
        return count;
    }

    @Override
    public int addStock(Integer id, Short num) {
        return goodsProductMapper.addStock(id, num);
    }
}
