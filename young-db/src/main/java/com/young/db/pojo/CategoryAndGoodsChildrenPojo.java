package com.young.db.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 首页展示商品分类和商品
 * @Author: yqz
 * @CreateDate: 2020/11/22 23:03
 */
@Data
public class CategoryAndGoodsChildrenPojo implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品简介")
    private String brief;

    @ApiModelProperty(value = "商品页面商品图片")
    private String picUrl;

    @ApiModelProperty(value = "是否人气推荐，如果设置则可以在人气推荐页面展示")
    private boolean isHot;

    @ApiModelProperty(value = "是否新品首发，如果设置则可以在新品首发页面展示")
    private boolean isNew;

    @ApiModelProperty(value = "专柜价格")
    private BigDecimal counterPrice;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;


}
