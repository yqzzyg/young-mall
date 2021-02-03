package com.young.mall.service;


import com.young.db.entity.YoungSeckillPromotion;

import java.util.List;

/**
 * @Description: 秒杀活动Service
 * @Author: yqz
 * @CreateDate: 2021/2/3 11:06
 */
public interface ClientSecondsToKillService {

    /**
     * 查询秒杀分类
     *
     * @param id
     * @return
     */
    List<YoungSeckillPromotion> seckillPromotionCategory(Integer id);

    /**
     * 根据category查询秒杀商品列表
     *
     * @param promotionId
     * @param page
     * @param size
     * @return
     */
    Object list(Integer promotionId, Integer page, Integer size);

    /**
     * 分页查询不同场次关联及商品信息
     *
     * @param promotionId        秒杀活动id
     * @param promotionSessionId 秒杀场次id
     * @param pageSize
     * @param pageNum
     * @return
     */
    Object listByDate(Long promotionId, Long promotionSessionId, Integer pageSize, Integer pageNum);

}
