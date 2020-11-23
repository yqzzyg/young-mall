package com.young.mall.domain;

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
public class RegisterDto {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "验证码不能为空")
    private String code;

    @NotEmpty(message = "微信用户code不能为空")
    private String wxCode;
}
