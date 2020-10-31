package com.young.mall.service;

import com.young.db.entity.YoungSearchHistory;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户搜索历史记录Service
 * @Author: yqz
 * @CreateDate: 2020/10/31 18:28
 */
public interface SearchHistoryService {

    /**
     * 用户搜索历史记录
     * @param userId 用户ID
     * @param keyword 关键词
     * @param page 起始页
     * @param size 分页大小
     * @param sort 排序依据
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungSearchHistory>> querySearchHistory(String userId, String keyword,
                                                          Integer page, Integer size,
                                                          String sort, String order);

}
