package com.young.mall.service;

import com.young.db.entity.YoungIssue;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 问题
 * @Author: yqz
 * @CreateDate: 2020/11/8 21:43
 */
public interface MallIssueService {

    /**
     * 分页查询
     *
     * @param question 关键词
     * @param page     分页
     * @param size     分页大小
     * @param sort     排序
     * @param order    排序方式
     * @return
     */
    Optional<List<YoungIssue>> querySelective(String question, Integer page,
                                              Integer size, String sort,
                                              String order);

    /**
     * 添加问题
     *
     * @param issue
     * @return
     */
    Optional<Integer> add(YoungIssue issue);

    /**
     * 读取问题详情
     *
     * @param id
     * @return
     */
    Optional<YoungIssue> findById(Integer id);

    /**
     * 更新通用问题
     *
     * @param issue
     * @return
     */
    Optional<Integer> update(YoungIssue issue);

    Optional<Integer> delete(Integer id);

    /**
     * 查询通用问题
     *
     * @return
     */
    List<YoungIssue> query();
}
