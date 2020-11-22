package com.young.mall.service;

import com.young.db.entity.YoungTopic;

import java.util.List;

/**
 * @Description: 专题Service
 * @Author: yqz
 * @CreateDate: 2020/11/22 18:08
 */
public interface ClientTopicService {

    /**
     * 查询专题
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungTopic> queryList(int page, int size);

    /**
     * 查询专题
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    List<YoungTopic> queryList(int page, int size, String sort, String order);
}
