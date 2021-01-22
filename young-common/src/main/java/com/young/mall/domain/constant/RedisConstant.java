package com.young.mall.domain.constant;


/**
 * @Description: redis缓存相关常量
 * @Author: yqz
 * @CreateDate: 2020/10/27 9:26
 */
public interface RedisConstant {

    /**
     * redis过期时间
     */
    Integer REDIS_EXPIRE=86400;

    /**
     * 后台用户信息缓存 key
     */
    String REDIS_KEY_ADMIN="young:admin";

    /**
     * client用户信息缓存 key
     */
    String REDIS_KEY_CLIENT="young:client";

    /**
     * 后台系统用户 角色 ID key
     */
    String REDIS_KEY_ROLEIDS="young:roles";
}
