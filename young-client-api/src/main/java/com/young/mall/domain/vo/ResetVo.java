package com.young.mall.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/22 11:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetVo implements Serializable {

    @NotEmpty(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String password;

    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String mobile;

    @NotEmpty(message = "手机验证码不能为空")
    @ApiModelProperty("手机验证码")
    private String code;
}
