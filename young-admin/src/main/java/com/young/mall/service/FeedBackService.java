package com.young.mall.service;

import com.young.db.entity.YoungFeedback;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/10/31 21:08
 */
public interface FeedBackService {

    /**
     * 查询意见反馈list
     * @param userId 用户ID
     * @param username 用户名
     * @param page 起始页
     * @param size 分页大小
     * @param sort 排序字段
     * @param order 排序方式
     * @return
     */
    Optional<List<YoungFeedback>> queryFeedBackList(Integer userId, String username,
                                                    Integer page, Integer size,
                                                    String sort, String order);
}
