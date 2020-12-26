package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 立即购买
 * @Author: yqz
 * @CreateDate: 2020/12/25 14:53
 */
@Data
public class FastAddVo implements Serializable {

    @ApiModelProperty("商品货品的数量")
    @NotNull(message = "商品货品的数量不能为空")
    private Integer number;

    @ApiModelProperty("商品货品表的货品ID")
    @NotNull(message = "商品货品表的货品ID不能为空")
    private Integer productId;

    @ApiModelProperty("商品表的商品ID")
    @NotNull(message = "商品表的商品ID不能为空")
    private Integer goodsId;
}
