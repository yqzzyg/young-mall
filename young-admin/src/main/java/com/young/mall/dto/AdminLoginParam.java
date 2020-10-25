package com.young.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String username;
    private String password;
}
