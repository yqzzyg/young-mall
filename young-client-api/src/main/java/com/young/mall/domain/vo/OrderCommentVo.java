package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 评论入参
 * @Author: yqz
 * @CreateDate: 2020/12/23 15:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCommentVo implements Serializable {

    @ApiModelProperty(value = "订单商品表的商品id")
    @NotEmpty(message = "订单商品表的商品id不能为空")
    private Integer orderGoodsId;

    @ApiModelProperty(value = "评论内容")
    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "评分， 1-5")
    @NotEmpty(message = "评分不能为空")
    private Integer star;

    @ApiModelProperty(value = "是否含有图片")
    private Boolean hasPicture;

    @ApiModelProperty(value = "图片地址列表，采用JSON数组格式")
    private List<String> picUrls;
}
