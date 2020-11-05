package com.young.mall.service;

import com.young.db.entity.YoungStorage;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 存储service
 * @Author: yqz
 * @CreateDate: 2020/11/5 15:37
 */
public interface YoungStorageService {

    /**
     * 删除
     * @param key
     */
    Optional<Integer> deleteByKey(String key);

    /**
     * 添加
     * @param storageInfo
     */
    Optional<Integer> add(YoungStorage storageInfo);

    /**
     * 查询，根据唯一索引
     * @param key
     * @return
     */
    YoungStorage findByKey(String key);

    /**
     * 更新
     * @param storageInfo
     * @return
     */
    Optional<Integer> update(YoungStorage storageInfo);

    /**
     * 查询 根据主键
     * @param id
     * @return
     */
    Optional<YoungStorage> findById(Integer id);

    /**
     * 模糊查询
     * @param key
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungStorage>> querySelective(String key, String name, Integer page, Integer limit, String sort,
                                    String order);
}
