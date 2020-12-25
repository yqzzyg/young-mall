package com.young.mall.service.impl;

import com.young.mall.annotation.CacheException;
import com.young.mall.service.ClientCacheService;
import com.young.mall.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 验证码缓存
 * @Author: yqz
 * @CreateDate: 2020/12/24 18:06
 */
@Service
public class ClientCacheServiceImpl implements ClientCacheService {

    @Resource
    private RedisService redisService;

    @CacheException
    @Override
    public Object getVerificationCode(String key) {
        return redisService.get(key);
    }

    @CacheException
    @Override
    public void setVerificationCode(String key, String value, long time) {
        redisService.set(key,value,time);
    }
}
