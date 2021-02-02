package com.young.mall.service;

import com.young.db.entity.YoungSeckillPromotionProductRelation;
import com.young.db.pojo.SeckillPromotionProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 秒杀商品关联管理Service
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:18
 */
public interface AdminSeckillProductRelationService {

    /**
     * 批量添加关联
     *
     * @param relationList
     * @return
     */
    @Transactional
    int create(List<YoungSeckillPromotionProductRelation> relationList);

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
