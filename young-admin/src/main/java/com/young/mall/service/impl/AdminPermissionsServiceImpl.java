package com.young.mall.service.impl;

import com.young.db.dao.YoungPermissionMapper;
import com.young.db.entity.YoungPermission;
import com.young.db.entity.YoungPermissionExample;
import com.young.mall.common.ResBean;
import com.young.mall.exception.Asserts;
import com.young.mall.service.AdminPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 权限业务
 * @Author: yqz
 * @CreateDate: 2020/11/19 17:52
 */
@Service
public class AdminPermissionsServiceImpl implements AdminPermissionsService {

    @Autowired
    private YoungPermissionMapper youngPermissionMapper;

    @Override
    public Optional<List<YoungPermission>> getPermissions(Integer roleId) {

        YoungPermissionExample example = new YoungPermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<YoungPermission> permissionList = youngPermissionMapper.selectByExample(example);
        return Optional.ofNullable(permissionList);
    }

    @Override
    public List<String> getPermissionsList(Integer roleId) {
        Optional<List<YoungPermission>> optional = getPermissions(roleId);
        if (!optional.isPresent()) {
            Asserts.fail("查询失败");
        }
        List<String> permissionList = optional.get().stream()
                .map(youngPermission -> youngPermission.getPermission())
                .collect(Collectors.toList());
        return permissionList;
    }
}
