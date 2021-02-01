package com.young.mall.service;

/**
 * @Description: 秒杀商品关联管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 22:18
 */
public interface AdminProductRelationService {


    /**
     * 根据活动和场次id获取商品关系数量
     *
     * @param promotionId
     * @param promotionSessionId
     * @return
     */
    long getCount(Long promotionId, Long promotionSessionId);
}
