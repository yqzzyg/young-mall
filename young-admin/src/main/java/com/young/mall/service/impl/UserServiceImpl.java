package com.young.mall.service.impl;

import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.YoungUserExample;
import com.young.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 用户service接口实现类
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private YoungUserMapper userMapper;

    @Override
    public Optional<Integer> count() {
        YoungUserExample example = new YoungUserExample();

        example.createCriteria().andDeletedEqualTo(false);

        long count = userMapper.countByExample(example);

        return Optional.ofNullable(((int) count));
    }
}
