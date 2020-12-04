package com.young.mall.service;

import com.young.db.entity.YoungFootprint;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:46
 */
public interface ClientFootPrintService {

    /**
     * 增加用户足迹
     *
     * @param footprint
     * @return
     */
    int add(YoungFootprint footprint);
}
