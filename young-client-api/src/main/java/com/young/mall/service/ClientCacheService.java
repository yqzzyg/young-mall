package com.young.mall.service;


/**
 * @Description: 验证码缓存业务
 * @Author: yqz
 * @CreateDate: 2020/12/24 18:03
 */
public interface ClientCacheService {

    /**
     * 获取验证码缓存
     * @param key
     * @return
     */
    Object getVerificationCode(String key);

    /**
     * 添加验证码缓存
     * @param key
     * @param value
     * @param time 秒
     */
    void setVerificationCode(String key, String value, long time);


    /**
     * 获取用户信息缓存
     * @param key
     * @return
     */
    Object getClientUser(String key);

    /**
     * 添加用户信息缓存
     * @param key
     * @param value
     * @param time 秒
     */
    void setClientUser(String key, Object value, long time);


}
