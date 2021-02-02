package com.young.db.pojo;

import com.young.db.entity.YoungSeckillPromotionProductRelation;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 秒杀及商品信息封装
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:19
 */
public class SeckillPromotionProduct extends YoungSeckillPromotionProductRelation {

    @ApiModelProperty("关联商品")
    private YoungGoodsPlus product;

    public YoungGoodsPlus getProduct() {
        return product;
    }

    public void setProduct(YoungGoodsPlus product) {
        this.product = product;
    }
}
