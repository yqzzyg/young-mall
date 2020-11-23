package com.young.mall.service;

import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.domain.RegisterDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/23 16:17
 */
public interface ClientUserService {

    /**
     * 用户注册
     *
     * @param registerDto
     * @return
     */
    ResBean register(RegisterDto registerDto, HttpServletRequest request);

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
     * 新增客户端用户
     *
     * @param youngUser
     * @return
     */
    Integer addUser(YoungUser youngUser);
}
