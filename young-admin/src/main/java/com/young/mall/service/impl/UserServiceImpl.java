package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.YoungUser;
import com.young.db.entity.YoungUserExample;
import com.young.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Optional<List<YoungUser>> queryUserList(String username, String mobile,
                                                   Integer page, Integer size,
                                                   String sort, String order) {

        YoungUserExample example = new YoungUserExample();
        YoungUserExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotBlank(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (StrUtil.isNotBlank(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotBlank(sort) && StrUtil.isNotBlank(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungUser> youngUsers = userMapper.selectByExample(example);

        return Optional.ofNullable(youngUsers);
    }
}
