package com.young.mall.service;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Description: 验证码缓存业务
 * @Author: yqz
 * @CreateDate: 2020/12/24 18:03
 */
public interface VerificationCodeCacheService {

    /**
     * 获取缓存
     * @param key
     * @return
     */
    Object getVerificationCode(String key);

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param time 秒
     */
    void setVerificationCode(String key, String value, long time);
}
