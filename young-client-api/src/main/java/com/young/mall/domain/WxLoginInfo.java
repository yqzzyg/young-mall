package com.young.mall.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description: 通过微信登录
 * @Author: yqz
 * @CreateDate: 2020/11/26 17:24
 */
@Data
public class WxLoginInfo implements Serializable {

    @NotEmpty(message = "微信code不能为空")
    private String code;

    private UserInfo userInfo;
    private Integer shareUserId;
}
