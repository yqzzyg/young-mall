package com.young.mall.service;

import com.young.db.pojo.SeckillPromotionProduct;

import java.util.List;

/**
 * @Description: 秒杀商品关联管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:18
 */
public interface AdminSeckillProductRelationService {

    /**
     * 分页查询相关商品及促销信息
     *
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<SeckillPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);

}
