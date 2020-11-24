package com.young.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/24 21:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientLoginDto implements Serializable {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
