package com.young.mall.service;

import com.young.db.entity.YoungKeyword;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 关键词service
 * @Author: yqz
 * @CreateDate: 2020/11/9 19:51
 */
public interface KeyWordService {

    /**
     * 分页查找
     *
     * @param keyword 关键词
     * @param url
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungKeyword>> querySelective(String keyword, String url,
                                                Integer page, Integer size,
                                                String sort, String order);

    /**
     * 创建关键词
     * @param keyword
     * @return
     */
    Optional<Integer> create(YoungKeyword keyword);

    /**
     * 读取关键词详情
     * @param id
     * @return
     */
    Optional<YoungKeyword> findById(Integer id);

    /**
     * 更新
     * @param keyword
     * @return
     */
    Optional<Integer> updateById(YoungKeyword keyword);

    /**
     * 删除
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
