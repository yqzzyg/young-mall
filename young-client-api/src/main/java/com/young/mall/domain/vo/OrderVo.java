package com.young.mall.domain.vo;

import com.young.mall.util.OrderHandleOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 订单视图
 * @Author: yqz
 * @CreateDate: 2020/12/21 14:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo implements Serializable {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("订单编号")
    private String orderSn;

    @ApiModelProperty("创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty("收货人名称")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty("收货具体地址")
    private String address;

    @ApiModelProperty("商品总费用")
    private BigDecimal goodsPrice;

    @ApiModelProperty("配送费用")
    private BigDecimal freightPrice;

    @ApiModelProperty("优惠减免")
    private BigDecimal discountPrice;

    @ApiModelProperty("实付费用， = order_price - integral_price")
    private BigDecimal actualPrice;

    @ApiModelProperty("商品信息")
    private String orderStatusText;

    @ApiModelProperty("选项")
    private OrderHandleOption handleOption;

    @ApiModelProperty("发货快递公司")
    private String expCode;

    @ApiModelProperty("发货编号")
    private String expNo;
}
