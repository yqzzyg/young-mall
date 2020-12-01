package com.young.mall.service;

import com.young.db.entity.YoungSearchHistory;

/**
 * @Description: 搜索历史
 * @Author: yqz
 * @CreateDate: 2020/12/1 20:59
 */
public interface ClientSearchHistoryService {

    /**
     * 新增搜索历史
     *
     * @param history
     * @return
     */
    Integer save(YoungSearchHistory history);
}
