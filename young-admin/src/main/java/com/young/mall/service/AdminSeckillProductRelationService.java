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
     * 删除关联
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改关联相关信息
     *
     * @param id
     * @param relation
     * @return
     */
    int update(Long id, YoungSeckillPromotionProductRelation relation);

    /**
     * 获取关联详情
     *
     * @param id
     * @return
     */
    YoungSeckillPromotionProductRelation getItem(Long id);


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

    /**
     * 根据活动和场次id获取商品关系数量
     *
     * @param promotionId
     * @param promotionSessionId
     * @return
     */
    long getCount(Long promotionId, Long promotionSessionId);
}
