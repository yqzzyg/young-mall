package com.young.mall.service.impl;

import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.YoungUser;
import com.young.db.entity.YoungUserExample;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description: 客户端用户相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/23 16:37
 */
@Service
public class ClientUserServiceImpl implements ClientUserService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungUserMapper youngUserMapper;

    @Override
    public List<YoungUser> getUserByName(String username) {

        YoungUserExample example = new YoungUserExample();
        example.createCriteria().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return youngUserMapper.selectByExample(example);
    }

    @Override
    public List<YoungUser> getUserByMobile(String mobile) {
        YoungUserExample example = new YoungUserExample();
        example.createCriteria().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return youngUserMapper.selectByExample(example);
    }

    @Override
    public List<YoungUser> getUserByOpenId(String openId) {
        YoungUserExample example = new YoungUserExample();
        example.createCriteria().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return youngUserMapper.selectByExample(example);
    }

    @Override
    public YoungUser getOneUserByOpenId(String openId) {
        YoungUserExample example = new YoungUserExample();
        example.createCriteria().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return youngUserMapper.selectOneByExample(example);
    }

    @Override
    public Integer addUser(YoungUser youngUser) {
        youngUser.setAddTime(LocalDateTime.now());
        youngUser.setUpdateTime(LocalDateTime.now());
        int count = youngUserMapper.insertSelective(youngUser);
        return count;
    }

    @Override
    public Integer updateById(YoungUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return youngUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public ClientUserDetails getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //判断当前是否用户是否在线
        if (principal instanceof ClientUserDetails) {
            ClientUserDetails user = (ClientUserDetails) principal;
            return user;
        }
        return null;
    }

    @Override
    public YoungUser findById(Integer id) {
        return youngUserMapper.selectByPrimaryKey(id);
    }
}
