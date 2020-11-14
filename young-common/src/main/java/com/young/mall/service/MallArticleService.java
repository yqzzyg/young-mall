package com.young.mall.service;

import com.young.db.entity.YoungArticle;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 公告推广业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 12:05
 */
public interface MallArticleService {

    /**
     * 分页查询公告
     *
     * @param title
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungArticle>> querySelective(String title,
                                                Integer page, Integer size,
                                                String sort, String order);


    /**
     * 根据公告ID查看公告
     *
     * @param aid
     * @return
     */
    Optional<YoungArticle> details(Integer aid);

    /**
     * 创建公告
     *
     * @param youngArticle
     * @return
     */
    Optional<Integer> create(YoungArticle youngArticle);

    /**
     * 更新公告
     *
     * @param article
     * @return
     */
    Optional<Integer> update(YoungArticle article);

    /**
     * 根据ID删除公告
     *
     * @param aid
     * @return
     */
    Optional<Integer> delete(Integer aid);

    /**
     * 校验是否已经存在同名公告
     *
     * @param title
     * @return
     */
    boolean checkExist(String title);
}
