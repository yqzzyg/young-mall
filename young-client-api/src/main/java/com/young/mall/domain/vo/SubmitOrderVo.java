package com.young.mall.domain.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/24 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOrderVo implements Serializable {

    @NotEmpty(message = "购物车id不能为空")
    @ApiModelProperty("购物车id")
    private Integer cartId;

    @NotEmpty(message = "收货地址id不能为空")
    @ApiModelProperty("收货地址id")
    private Integer addressId;

    @NotEmpty(message = "优惠券id不能为空")
    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("用户订单留言")
    private String message;

    @NotEmpty(message = "团购id不能为空")
    @ApiModelProperty("团购id")
    private Integer grouponRulesId;

    @NotEmpty(message = "团购参与者id不能为空")
    @ApiModelProperty("团购参与者id")
    private Integer grouponLinkId;
}
