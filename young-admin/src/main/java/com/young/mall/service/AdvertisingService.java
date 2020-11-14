package com.young.mall.service;

import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungAdExample;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 广告相关业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 18:40
 */
public interface AdvertisingService {

    /**
     * 分页查询广告
     *
     * @param name
     * @param content
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungAd>> list(String name, String content,
                                 Integer page, Integer size,
                                 String sort,
                                 String order);

    /**
     * 创建广告
     *
     * @param youngAd
     * @return
     */
    Optional<Integer> create(YoungAd youngAd);

    /**
     * 读取
     *
     * @param id
     * @return
     */
    Optional<YoungAd> findById(Integer id);

    /**
     * 更新
     *
     * @param youngAd
     * @return
     */
    Optional<Integer> update(YoungAd youngAd);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Optional<Integer> delete(Integer id);
}
