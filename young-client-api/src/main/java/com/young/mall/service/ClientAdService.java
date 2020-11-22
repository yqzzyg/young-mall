package com.young.mall.service;

import com.young.db.entity.YoungAd;

import java.util.List;

/**
 * @Description: 广告相关
 * @Author: yqz
 * @CreateDate: 2020/11/21 20:34
 */
public interface ClientAdService {

    /**
     * 查询所有广告
     *
     * @return
     */
    List<YoungAd> queryIndex();
}
