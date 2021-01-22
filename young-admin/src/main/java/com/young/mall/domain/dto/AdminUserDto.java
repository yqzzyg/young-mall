package com.young.mall.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
