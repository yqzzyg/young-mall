package com.young.mall.service;

import com.young.db.entity.YoungArticle;

import java.util.List;

/**
 * @Description: 客户端文章相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/22 12:40
 */
public interface ClientArticleService {

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    List<YoungArticle> queryList(int page, int size, String sort, String order);

    /**
     * 通过id查询公告
     *
     * @param id
     * @return
     */
    YoungArticle findById(Integer id);
}
