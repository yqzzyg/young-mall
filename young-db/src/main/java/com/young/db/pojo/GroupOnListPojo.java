package com.young.db.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 联表查询团购活动
 * @Author: yqz
 * @CreateDate: 2020/11/17 10:51
 */
@Data
public class GroupOnListPojo implements Serializable {

    @ApiModelProperty(value = "young_groupon_rules表的订单goods_sn")
    private String goodsSn;
    @ApiModelProperty(value = "young_groupon_rules表的订单goods_name")
    private String goodsName;
    @ApiModelProperty(value = "young_groupon表的订单id")
    private Integer orderId;
    @ApiModelProperty(value = "young_groupon表的用户Id")
    private Integer userId;
    @ApiModelProperty(value = "young_groupon的团购分享图片地址")
    private String shareUrl;
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;
    @ApiModelProperty(value = "达到优惠条件的人数")
    private Integer discountMember;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime addTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expireTime;
}
