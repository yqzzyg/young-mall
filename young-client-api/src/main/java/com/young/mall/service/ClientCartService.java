package com.young.mall.service;

import com.young.db.entity.YoungCart;
import com.young.mall.common.ResBean;

import java.util.List;
import java.util.Map;

/**
 * @Description: 购物车业务
 * @Author: yqz
 * @CreateDate: 2020/11/28 16:52
 */
public interface ClientCartService {

    /**
     * 根据用户id查询购物车
     *
     * @param uid 用户主键id
     * @return
     */
    List<YoungCart> queryByUid(Integer uid);

    /**
     * 购物车首页
     *
     * @param uid 用户主键id
     * @return
     */
    ResBean<Map<String, Object>> index(Integer uid);
}
