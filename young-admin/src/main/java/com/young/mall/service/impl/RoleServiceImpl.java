package com.young.mall.service.impl;

import cn.hutool.json.JSONUtil;
import com.young.db.dao.YoungRoleMapper;
import com.young.db.entity.YoungRole;
import com.young.db.entity.YoungRoleExample;
import com.young.mall.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: RoleService实现类
 * @Author: yqz
 * @CreateDate: 2020/10/29 12:13
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungRoleMapper roleMapper;

    @Override
    public Optional<Set<String>> getRolesByIds(Integer[] ids) {

        Set<String> roles = new HashSet<>();

        if (ids.length == 0) {
            return Optional.ofNullable(null);
        }
        YoungRoleExample example = new YoungRoleExample();

        example.createCriteria().andIdIn(Arrays.asList(ids)).andDeletedEqualTo(false)
                .andEnabledEqualTo(true);

        List<YoungRole> roleList = roleMapper.selectByExample(example);
        logger.error("数据库中查询出roles:{}", JSONUtil.toJsonStr(roleList));
        for (YoungRole youngRole : roleList) {
            roles.add(youngRole.getName());
        }
        return Optional.ofNullable(roles);
    }
}
