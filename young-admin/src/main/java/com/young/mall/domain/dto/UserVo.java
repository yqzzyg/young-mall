package com.young.mall.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户
 * @Author: yqz
 * @CreateDate: 2020/11/2 11:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo implements Serializable {

    private String nickname;
    private String avatar;
}
