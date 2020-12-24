package com.young.mall.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解，有该注解的缓存方法会抛出异常
 * @Author: yqz
 * @CreateDate: 2020/12/24 16:14
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
