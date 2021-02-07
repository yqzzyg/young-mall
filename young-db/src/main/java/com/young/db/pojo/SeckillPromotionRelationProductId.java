package com.young.db.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 包装商品id
 * @Author: yqz
 * @CreateDate: 2021/2/7 13:39
 */
@Data
public class SeckillPromotionRelationProductId {

    @ApiModelProperty("商品id")
    private Long productId;
}
