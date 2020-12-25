package com.young.mall.config.aspect;

import com.young.mall.annotation.CacheException;
import com.young.mall.exception.Asserts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @Description: Redis缓存切面，防止Redis宕机影响正常业务逻辑
 * @Author: yqz
 * @CreateDate: 2020/12/24 16:10
 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);

    /**
     * 此处需要使用 *CacheService 匹配切点，不要使用具体类名，因为该Module为公共的，如果使用具体的类名匹配，有可能会导致单独启动一个Mudole会报
     * "Caused by: java.lang.IllegalArgumentException: warning no match for this type name:" 异常，原因是切面表达式错误
     */
    @Pointcut("execution(public * com.young.mall.service.*CacheService.*(..)) || execution(public * com.young.mall.service.*CacheService.*(..))")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = Optional.ofNullable(null);
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            //有CacheException注解的方法需要抛出异常
            if (method.isAnnotationPresent(CacheException.class)) {
                Asserts.fail("redis缓存异常");
            } else {
                LOGGER.error(throwable.getMessage());
            }
        }
        return result;
    }
}
