package com.young.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.young.db.dao.YoungPermissionMapper;
import com.young.db.entity.YoungPermission;
import com.young.db.entity.YoungPermissionExample;
import com.young.mall.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:37
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungPermissionMapper permissionMapper;

    @Override
    public Optional<Set<String>> queryByRoleIds(Integer[] roleIds) {

        Set<String> permissions = new HashSet<>();
        if (roleIds.length == 0) {
            return Optional.ofNullable(permissions);
        }

        YoungPermissionExample example = new YoungPermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<YoungPermission> permissionList = permissionMapper.selectByExample(example);
        logger.info("从数据库查出的permissions：{}", JSONUtil.toJsonStr(permissionList));
        if (CollectionUtil.isNotEmpty(permissionList)) {
            for (YoungPermission permission : permissionList) {
                permissions.add(permission.getPermission());
            }

            return Optional.ofNullable(permissions);
        }
        return Optional.ofNullable(null);
    }
}
