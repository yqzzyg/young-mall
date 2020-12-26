package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungAdmin;
import com.young.mall.constant.RedisConstant;
import com.young.mall.service.AdminCacheService;
import com.young.mall.service.AdminService;
import com.young.mall.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Description: 后台用户缓存操作类
 * @Author: yqz
 * @CreateDate: 2020/12/24 17:41
 */
@Service
public class AdminCacheServiceImpl implements AdminCacheService {

    @Resource
    private RedisService redisService;

    @Resource
    private AdminService adminService;

    @Override
    public void delAdmin(Integer adminId) {

        YoungAdmin admin = adminService.findAdminById(adminId);
        if (!BeanUtil.isEmpty(admin)) {
            redisService.del(RedisConstant.REDIS_KEY_ADMIN + ":" + admin.getUsername());
        }
    }

    @Override
    public Object getAdmin(String username) {
        return redisService.get(RedisConstant.REDIS_KEY_ADMIN + ":" + username);
    }

    @Override
    public void setAdmin(YoungAdmin admin) {
        redisService.set(RedisConstant.REDIS_KEY_ADMIN + ":" + admin.getUsername(), admin, RedisConstant.REDIS_EXPIRE);
    }

    @Override
    public Object getPermissionsList(String username) {

        return redisService.get(RedisConstant.REDIS_KEY_ROLEIDS + ":" + username);

    }

    @Override
    public void setResourceList(String username, Set<String> permissionsList) {
        redisService.set(RedisConstant.REDIS_KEY_ROLEIDS + ":" + username, permissionsList, RedisConstant.REDIS_EXPIRE);
    }
}
