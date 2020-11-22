package com.young.mall.service.impl;

import com.young.db.dao.YoungUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/21 22:03
 */
@Service
public class ClientUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private YoungUserMapper youngUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
