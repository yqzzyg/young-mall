package com.young.mall.domain.dto;

import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsAttribute;
import com.young.db.entity.YoungGoodsProduct;
import com.young.db.entity.YoungGoodsSpecification;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description: 创建商品封装
 * @Author: yqz
 * @CreateDate: 2020/11/10 21:48
 */
@Data
public class GoodsArguments {

    @Valid
    private YoungGoods goods;
    private List<@Valid YoungGoodsSpecification> specifications;
    private List<@Valid YoungGoodsAttribute> attributes;
    private List<@Valid YoungGoodsProduct> products;

}
