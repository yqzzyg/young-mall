package com.young.mall.service.impl;

import com.young.db.dao.YoungRoleMapper;
import com.young.db.entity.YoungRole;
import com.young.db.entity.YoungRoleExample;
import com.young.mall.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 角色管理
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:37
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Autowired
    private YoungRoleMapper youngRoleMapper;

    @Override
    public Optional<List<YoungRole>> listAll() {
        YoungRoleExample example = new YoungRoleExample();
        example.createCriteria().andDeletedEqualTo(false);
        List<YoungRole> list = youngRoleMapper.selectByExample(example);
        return Optional.ofNullable(list);
    }
}
