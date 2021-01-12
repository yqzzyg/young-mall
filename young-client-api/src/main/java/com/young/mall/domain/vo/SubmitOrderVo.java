package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/24 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubmitOrderVo implements Serializable {

    @NotNull(message = "购物车id不能为空")
    @ApiModelProperty("购物车id")
    private List<Integer> cartIds;

    @NotNull(message = "收货地址id不能为空")
    @ApiModelProperty("收货地址id")
    private Integer addressId;

    @NotNull(message = "优惠券id不能为空")
    @ApiModelProperty("优惠券id")
    private Integer couponId;

    @ApiModelProperty("用户订单留言")
    private String message;

    @NotNull(message = "团购id不能为空")
    @ApiModelProperty("团购id")
    private Integer grouponRulesId;

    @NotNull(message = "团购参与者id不能为空")
    @ApiModelProperty("团购参与者id")
    private Integer grouponLinkId;
}
