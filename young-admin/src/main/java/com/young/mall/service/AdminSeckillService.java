package com.young.mall.service;

import com.young.db.entity.YoungSeckillPromotion;

import java.util.List;

/**
 * @Description: 秒杀活动管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 16:17
 */
public interface AdminSeckillService {

    /**
     * 分页查询活动
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<YoungSeckillPromotion> list(String keyword, Integer pageSize, Integer pageNum);
}
