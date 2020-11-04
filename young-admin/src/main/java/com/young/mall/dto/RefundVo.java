package com.young.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/4 11:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundVo {

    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Integer orderId;

    @NotEmpty(message = "退款金额不能为空")
    @ApiModelProperty(value = "退款金额", required = true)
    private String refundMoney;
}
