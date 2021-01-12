package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2021/1/12 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutVo implements Serializable {

    @ApiModelProperty("购物车id数组")
    @NotNull(message = "购物车id不能为空")
    private List<Integer> cartIds;

    @ApiModelProperty("收货地址id")
    @NotNull(message = "收货地址id不能为空")
    private Integer addressId;

    @ApiModelProperty("优惠券id")
    @NotNull(message = "优惠券id不能为空")
    private Integer couponId;

    @ApiModelProperty("团购规则id")
    @NotNull(message = "团购规则id不能为空")
    private Integer grouponRulesId;
}
