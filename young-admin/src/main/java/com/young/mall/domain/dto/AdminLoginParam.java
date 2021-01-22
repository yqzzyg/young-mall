package com.young.mall.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminLoginParam {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "username", required = true)
    private String username;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "password", required = true)
    private String password;
}
