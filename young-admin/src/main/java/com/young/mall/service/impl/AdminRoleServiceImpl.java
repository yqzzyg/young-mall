package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungRoleMapper;
import com.young.db.entity.YoungRole;
import com.young.db.entity.YoungRoleExample;
import com.young.mall.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public Optional<List<YoungRole>> list(String roleName, Integer page, Integer size, String sort, String order) {

        YoungRoleExample example = new YoungRoleExample();
        YoungRoleExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(roleName)) {
            criteria.andNameLike("%" + roleName + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungRole> youngRoleList = youngRoleMapper.selectByExample(example);

        return Optional.ofNullable(youngRoleList);
    }

    @Override
    public Optional<YoungRole> findById(Integer rid) {

        YoungRole youngRole = youngRoleMapper.selectByPrimaryKey(rid);
        return Optional.ofNullable(youngRole);
    }

    @Override
    public Optional<Integer> create(YoungRole youngRole) {
        youngRole.setAddTime(LocalDateTime.now());
        youngRole.setUpdateTime(LocalDateTime.now());
        int count = youngRoleMapper.insertSelective(youngRole);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> updateById(YoungRole youngRole) {
        youngRole.setUpdateTime(LocalDateTime.now());
        int count = youngRoleMapper.updateByPrimaryKeySelective(youngRole);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> deleteById(Integer rid) {

        int count = youngRoleMapper.logicalDeleteByPrimaryKey(rid);
        return Optional.ofNullable(count);
    }

    @Override
    public boolean checkExist(String roleName) {

        YoungRoleExample example = new YoungRoleExample();
        example.createCriteria().andNameEqualTo(roleName).andDeletedEqualTo(false);
        boolean flag = youngRoleMapper.selectByExample(example).size() != 0;
        return flag;
    }
}
