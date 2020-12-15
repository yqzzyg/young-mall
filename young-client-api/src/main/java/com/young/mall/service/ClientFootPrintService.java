package com.young.mall.service;

import com.young.db.entity.YoungFootprint;

import java.util.List;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:46
 */
public interface ClientFootPrintService {

    /**
     * 增加用户足迹
     *
     * @param footprint
     * @return
     */
    int add(YoungFootprint footprint);

    /**
     * 查询用户足迹
     *
     * @param userId 用户id
     * @param page   分页
     * @param size   分页大小
     * @return 足迹列表
     */
    List<YoungFootprint> queryByAddTime(Integer userId, Integer page, Integer size);

    /**
     * 获取某个商品信息,包含完整信息
     *
     * @param id 足迹id
     * @return
     */
    YoungFootprint findById(Integer id);

    /**
     * 通过主键id删除足迹
     *
     * @param id 足迹id
     * @return
     */
    Integer deleteById(Integer id);
}
