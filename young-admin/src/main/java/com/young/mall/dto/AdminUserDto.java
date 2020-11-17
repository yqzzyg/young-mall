package com.young.mall.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 用于展示后台管理
 * @Author: yqz
 * @CreateDate: 2020/11/17 16:04
 */
@Data
public class AdminUserDto implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "管理员名称")
    private String username;

    @ApiModelProperty(value = "头像图片")
    private String avatar;

    @ApiModelProperty(value = "角色列表")
    private Integer[] roleIds;

}
