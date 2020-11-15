package com.young.mall.service;

import com.young.db.entity.YoungTopic;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 专题service
 * @Author: yqz
 * @CreateDate: 2020/11/15 22:33
 */
public interface MallTopicService {

    /**
     * 分页查询专题
     *
     * @param title
     * @param subtitle
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungTopic>> list(String title, String subtitle,
                                    Integer page, Integer size,
                                    String sort, String order);

    /**
     * 添加专题
     *
     * @param youngTopic
     * @return
     */
    Optional<Integer> create(YoungTopic youngTopic);

    /**
     * 根据 id 查询专题
     *
     * @param id
     * @return
     */
    Optional<YoungTopic> findById(Integer id);

    /**
     * 更新专题
     *
     * @param youngTopic
     * @return
     */
    Optional<Integer> update(YoungTopic youngTopic);

    /**
     * 删除专题
     *
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
