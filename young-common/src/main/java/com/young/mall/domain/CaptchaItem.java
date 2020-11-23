package com.young.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description: 缓存系统中的验证码
 * @Author: yqz
 * @CreateDate: 2020/11/23 16:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaItem {
    private String phoneNumber;
    private String code;
    private LocalDateTime expireTime;
}
