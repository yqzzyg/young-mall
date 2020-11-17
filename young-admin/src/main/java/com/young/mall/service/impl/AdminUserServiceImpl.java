package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungAdminMapper;
import com.young.db.entity.YoungAdmin;
import com.young.db.entity.YoungAdminExample;
import com.young.mall.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 后台用户service
 * @Author: yqz
 * @CreateDate: 2020/11/17 15:16
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {


    @Autowired
    private YoungAdminMapper youngAdminMapper;

    @Override
    public Optional<List<YoungAdmin>> list(String username,
                                           Integer page, Integer size,
                                           String sort, String order) {

        YoungAdminExample example = new YoungAdminExample();
        YoungAdminExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungAdmin> adminList = youngAdminMapper.selectByExample(example);
        return Optional.ofNullable(adminList);
    }

    @Override
    public Optional<List<YoungAdmin>> findAdminByName(String username) {
        YoungAdminExample example = new YoungAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<YoungAdmin> youngAdminList = youngAdminMapper.selectByExample(example);
        return Optional.ofNullable(youngAdminList);
    }

    @Override
    public Optional<Integer> create(YoungAdmin youngAdmin) {
        youngAdmin.setAddTime(LocalDateTime.now());
        youngAdmin.setUpdateTime(LocalDateTime.now());
        int count = youngAdminMapper.insertSelective(youngAdmin);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungAdmin> findById(Integer id) {
        YoungAdmin youngAdmin = youngAdminMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(youngAdmin);
    }

    @Override
    public Optional<Integer> updateById(YoungAdmin youngAdmin) {
        youngAdmin.setUpdateTime(LocalDateTime.now());
        int count = youngAdminMapper.updateByPrimaryKeySelective(youngAdmin);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {
        int count = youngAdminMapper.logicalDeleteByPrimaryKey(id);
        return Optional.ofNullable(count);
    }
}
