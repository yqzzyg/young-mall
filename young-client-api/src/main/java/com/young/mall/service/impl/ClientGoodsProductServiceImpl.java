package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungCart;
import com.young.db.entity.YoungGoodsProduct;
import com.young.db.mapper.GoodsProductMapper;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientGoodsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public Integer reduceStock(YoungCart checkGoods) {

        Integer productId = checkGoods.getProductId();

        YoungGoodsProduct product = this.findById(productId);

        Integer remainNumber = product.getNumber() - checkGoods.getNumber();
        if (remainNumber < 0) {
            Asserts.fail(checkGoods.getGoodsName() + "下单的商品货品数量大于库存量");
        }

        int count = goodsProductMapper.reduceStock(checkGoods.getProductId(), checkGoods.getNumber(), product.getVersion());
        if (count != 1) {
            logger.error("{}:库存不足", checkGoods.getGoodsName());
            Asserts.fail(checkGoods.getGoodsName() + ":库存不足");
        }
        // 每次需将商品的销售量加下
        goodsProductMapper.addSales(checkGoods.getGoodsId(), checkGoods.getNumber());
        return count;
    }

    @Override
    public int addStock(Integer id, Short num) {
        return goodsProductMapper.addStock(id, num);
    }
}
