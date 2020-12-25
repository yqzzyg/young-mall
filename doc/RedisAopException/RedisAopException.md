# 使用Redis+AOP优化权限管理功能

项目中的权限管理功能有性能问题，因为每次访问接口进行权限校验时都会从数据库中去查询用户信息。最近对这个问题进行了优化，通过Redis+AOP解决了该问题，下面来讲下我的优化思路。

## 一、使用Redis作为缓存

> 对于上面的问题，最容易想到的就是把用户信息和用户资源信息存入到Redis中去，避免频繁查询数据库，本文的优化思路大体也是这样的。

首先我们需要对Spring Security中获取用户信息的方法添加缓存，我们先来看下这个方法执行了哪些数据库查询操作。

```java
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        YoungAdmin youngAdmin = getYoungAdmin(username);

        AdminUser adminUser = new AdminUser();
        BeanUtil.copyProperties(youngAdmin, adminUser);
        Set<GrantedAuthority> authoritySet = getAuthorities(adminUser);
        adminUser.setAuthorities(authoritySet);

        return adminUser;
    }
```



主要是获取用户信息和获取用户的资源信息这两个操作，接下来我们需要给这两个操作添加缓存操作，这里使用的是RedisTemple的操作方式。当查询数据时，先去Redis缓存中查询，如果Redis中没有，再从数据库查询，查询到以后在把数据存储到Redis中去。

```java
  /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    public YoungAdmin getYoungAdmin(String username) {

        YoungAdmin youngAdmin = null;

        YoungAdmin admin = adminCacheService.getAdmin(username);
        if (!BeanUtil.isEmpty(admin)) {
            logger.info("缓存中获取用户信息：{}", JSONUtil.toJsonStr(admin));
            return admin;
        }
        Optional<YoungAdmin> adminOptional = adminService.findAdminByName(username);
        if (!adminOptional.isPresent()) {
            Asserts.fail("无该用户");
        }
        youngAdmin = adminOptional.get();
        logger.info("数据库中获取用户信息：{}", JSONUtil.toJsonStr(youngAdmin));

        adminCacheService.setAdmin(admin);
        return youngAdmin;
    }

    /**
     * 查询 authoritySet
     *
     * @param adminUser
     * @return
     */
    public Set<GrantedAuthority> getAuthorities(AdminUser adminUser) {

        Set<String> permissionsList = adminCacheService.getPermissionsList(adminUser.getUsername());
        if (!BeanUtil.isEmpty(permissionsList)) {
            return unmodifiableSet(permissionsList);
        }

        Optional<Set<String>> optionalSet = permissionService.queryByRoleIds(adminUser.getRoleIds());
        Set<String> permissions = optionalSet.get();
        if (!optionalSet.isPresent()) {
            Asserts.fail("查询权限失败");
        }

        //把权限id存入缓存，避免每次查询数据库缓慢
        adminCacheService.setResourceList(adminUser.getUsername(),permissions);

        Set<GrantedAuthority> authoritySet = unmodifiableSet(permissions);

        return authoritySet;
    }



```



## 二、分析

上面这种查询操作其实用Spring Cache来操作更简单，直接使用@Cacheable即可实现，为什么还要使用RedisTemplate来直接操作呢？因为作为缓存，我们所希望的是，如果Redis宕机了，我们的业务逻辑不会有影响，而使用Spring Cache来实现的话，当Redis宕机以后，用户的登录等种种操作就会都无法进行了。

由于我们把用户信息和用户资源信息都缓存到了Redis中，所以当我们修改用户信息和资源信息时都需要删除缓存中的数据，具体什么时候删除，查看缓存业务类的注释即可。

```java
package com.young.mall.service;

import com.young.db.entity.YoungAdmin;

import java.util.Set;

/**
 * @Description: 后台用户缓存操作类
 * @Author: yqz
 * @CreateDate: 2020/12/24 17:36
 */
public interface AdminCacheService {

    /**
     * 删除后台用户缓存
     *
     * @param adminId
     */
    void delAdmin(Integer adminId);


    /**
     * 获取缓存后台用户信息
     *
     * @param username
     */
    YoungAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     *
     * @param admin
     */
    void setAdmin(YoungAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     *
     * @param username
     * @return
     */
    Set<String> getPermissionsList(String username);

    /**
     * 设置后台后台用户资源列表
     *
     * @param username
     * @param permissionsList
     */
    void setResourceList(String username, Set<String> permissionsList);
}

package com.young.mall.service;

import com.young.db.entity.YoungAdmin;

import java.util.Set;

/**
 * @Description: 后台用户缓存操作类
 * @Author: yqz
 * @CreateDate: 2020/12/24 17:36
 */
public interface AdminCacheService {

    /**
     * 删除后台用户缓存
     *
     * @param adminId
     */
    void delAdmin(Integer adminId);


    /**
     * 获取缓存后台用户信息
     *
     * @param username
     */
    YoungAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     *
     * @param admin
     */
    void setAdmin(YoungAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     *
     * @param username
     * @return
     */
    Set<String> getPermissionsList(String username);

    /**
     * 设置后台后台用户资源列表
     *
     * @param username
     * @param permissionsList
     */
    void setResourceList(String username, Set<String> permissionsList);
}

```



## 三、新问题

经过上面的一系列优化之后，性能问题解决了。但是引入新的技术之后，新的问题也会产生，比如说当Redis宕机以后，我们直接就无法登录了，下面我们使用AOP来解决这个问题。



# 使用AOP处理缓存操作异常

>为什么要用AOP来解决这个问题呢？因为我们的缓存业务类`RedisService`已经写好了，要保证缓存业务类中的方法执行不影响正常的业务逻辑，就需要在所有方法中添加`try catch`逻辑。使用AOP，我们可以在一个地方写上`try catch`逻辑，然后应用到所有方法上去。试想下，我们如果又多了几个缓存业务类，只要配置下切面即可，这波操作多方便！



## 一、定义切面

首先我们先定义一个切面，在相关缓存业务类上面应用，在它的环绕通知中直接处理掉异常，保障后续操作能执行。

```java
package com.young.mall.config.aspect;

import com.young.mall.annotation.CacheException;
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
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = Optional.ofNullable(null);
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            //有CacheException注解的方法需要抛出异常
            if (method.isAnnotationPresent(CacheException.class)) {
                throw throwable;
            } else {
                LOGGER.error(throwable.getMessage());
            }
        }
        return result;
    }
}
```



这样处理之后，就算我们的Redis宕机了，我们的业务逻辑也能正常执行。

不过并不是所有的方法都需要处理异常的，比如我们的验证码存储，如果我们的Redis宕机了，我们的验证码存储接口需要的是报错，而不是返回执行成功。

## 二、自定义注解

对于上面这种需求我们可以通过自定义注解来完成，首先我们自定义一个`CacheException`注解，如果方法上面有这个注解，发生异常则直接抛出。

```java
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

```



## 三、使用自定义注解

接下来我们需要把`@CacheException`注解应用到存储和获取验证码的方法上去，这里需要注意的是要应用在实现类上而不是接口上，因为`isAnnotationPresent`方法只能获取到当前方法上的注解，而不能获取到它实现接口方法上的注解。

```java

package com.young.mall.service.impl;

import com.young.mall.annotation.CacheException;
import com.young.mall.service.RedisService;
import com.young.mall.service.ClientCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: 验证码缓存
 * @Author: yqz
 * @CreateDate: 2020/12/24 18:06
 */
@Service
public class VerificationCodeCacheServiceImpl implements VerificationCodeCacheService {

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

```



## 四、总结

对于影响性能的，频繁查询数据库的操作，我们可以通过Redis作为缓存来优化。缓存操作不该影响正常业务逻辑，我们可以使用AOP来统一处理缓存操作中的异常。
