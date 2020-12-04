package com.young.mall.service;

/**
 * @Description: 用户收藏
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:39
 */
public interface ClientCollectService {

    /**
     * 根据用户id和商品id查询
     *
     * @param uid
     * @param gid
     * @return
     */
    int count(Integer uid, Integer gid);
}
