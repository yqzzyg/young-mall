package com.young.mall.service;

import com.young.db.entity.YoungUser;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户service接口
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:41
 */
public interface UserService {

    /**
     * 用户数
     * @return
     */
    Optional<Integer> count();

    Optional<List<YoungUser>> queryUserList(String username, String mobile, Integer page, Integer size, String sort,
                                        String order);
}
