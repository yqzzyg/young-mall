package com.young.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 用户信息
 * @Author: yqz
 * @CreateDate: 2020/11/23 21:41
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -5813029516433359765L;

    private Integer userId;
    private String nickName;
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private String language;
    private Byte gender;
    private String phone;
    // 用户层级 0 普通用户，1 VIP用户，2 区域代理用户
    private Byte userLevel;
    // 代理用户描述
    private String userLevelDesc;

    private Byte status;//状态
    private String registerDate;//注册日期
}
