package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 评论入参
 * @Author: yqz
 * @CreateDate: 2020/12/23 15:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo implements Serializable {

    @ApiModelProperty(value = "商品表的商品id")
    private Integer valueId;

    @ApiModelProperty(value = "评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论；如果type=3，则是订单商品评论。")
    private Byte type;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "掌柜回复")
    private String replyContent;

    @ApiModelProperty(value = "用户表的用户ID")
    private Integer userId;

    @ApiModelProperty(value = "是否含有图片")
    private Boolean hasPicture;

    @ApiModelProperty(value = "图片地址列表，采用JSON数组格式")
    private String[] picUrls;

    @ApiModelProperty(value = "评分， 1-5")
    private Short star;
}
