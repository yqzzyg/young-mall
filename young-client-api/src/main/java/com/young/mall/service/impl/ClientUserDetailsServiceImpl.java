package com.young.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.YoungUser;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/21 22:03
 */
@Service
public class ClientUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientUserService clientUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<YoungUser> userList = clientUserService.getUserByName(username);
        if (CollectionUtil.isEmpty(userList)) {
            Asserts.fail("无该用户");
        }
        YoungUser youngUser = userList.get(0);
        ClientUserDetails userDetails = new ClientUserDetails(youngUser);
        return userDetails;
    }
}
