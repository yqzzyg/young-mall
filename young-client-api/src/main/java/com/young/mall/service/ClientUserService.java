package com.young.mall.service;

import com.young.db.entity.YoungUser;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/23 16:17
 */
public interface ClientUserService {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    List<YoungUser> getUserByName(String username);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    List<YoungUser> getUserByMobile(String mobile);

    /**
     * 根据微信openid查询用户
     *
     * @param openId
     * @return
     */
    List<YoungUser> getUserByOpenId(String openId);

    /**
     * 根据微信openid查询一个用户
     *
     * @param openId
     * @return
     */
    YoungUser getOneUserByOpenId(String openId);

    /**
     * 新增客户端用户
     *
     * @param youngUser
     * @return
     */
    Integer addUser(YoungUser youngUser);

    /**
     * 通过主键ID更新用户表
     *
     * @param user
     * @return
     */
    Integer updateById(YoungUser user);
}
