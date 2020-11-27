package com.young.mall.service.impl;

import com.young.mall.domain.AdminUser;
import com.young.mall.exception.Asserts;
import com.young.mall.service.AuthService;
import com.young.mall.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:04
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Override
    public String login(String username, String password) {
        logger.debug("进入AuthServiceImpl login方法,入参：username：{}，password：{}",username,password);
        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2 认证。调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authenticate = null;
        try {
            authenticate = this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                Asserts.fail("密码错误");
            } else {
                Asserts.fail("用户名错误");
            }
        }
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 4 生成自定义token
        String token = jwtTokenUtil.generateToken((UserDetails) authenticate.getPrincipal());

        return token;
    }

    @Override
    public AdminUser getUserInfo() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //判断当前是否用户是否在线
        if (principal instanceof AdminUser) {
            AdminUser user = (AdminUser) principal;
            return user;
        }
        return null;
    }
}
