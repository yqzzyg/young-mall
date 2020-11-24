package com.young.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 用户端注册入参
 * @Author: yqz
 * @CreateDate: 2020/11/23 16:11
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientUserDto {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("手机号")
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty("短信验证码")
    @NotEmpty(message = "验证码不能为空")
    private String code;

    @ApiModelProperty("微信用户code")
    @NotEmpty(message = "微信用户code不能为空")
    private String wxCode;
}
