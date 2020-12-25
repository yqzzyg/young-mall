package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.young.db.entity.YoungUser;
import com.young.mall.constant.RedisConstant;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientCacheService;
import com.young.mall.service.ClientUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/21 22:03
 */
@Service
public class ClientUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ClientUserService clientUserService;

    @Resource
    private ClientCacheService clientCacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        YoungUser youngUser = (YoungUser) clientCacheService.getClientUser(RedisConstant.REDIS_KEY_CLIENT + ":" + username);

        if (!BeanUtil.isEmpty(youngUser)) {
            return new ClientUserDetails(youngUser);
        }

        List<YoungUser> userList = clientUserService.getUserByName(username);
        if (CollectionUtil.isEmpty(userList)) {
            Asserts.fail("无该用户");
        }
        youngUser = userList.get(0);
        clientCacheService.setClientUser(RedisConstant.REDIS_KEY_CLIENT + ":" + username, youngUser, RedisConstant.REDIS_EXPIRE);
        ClientUserDetails userDetails = new ClientUserDetails(youngUser);
        return userDetails;
    }
}
