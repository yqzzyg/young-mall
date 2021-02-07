package com.young.db.mapper;

import com.young.db.pojo.SeckillPromotionProduct;
import com.young.db.pojo.SeckillPromotionRelationProduct;
import com.young.db.pojo.SeckillPromotionSessionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 自定义秒杀商品关系管理Dao
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:30
 */
public interface SeckillPromotionProductRelationMapper {

    /**
     * 获取秒杀及相关商品信息
     *
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @return
     */
    List<SeckillPromotionProduct> getSeckillGoodsList(@Param("flashPromotionId") Long flashPromotionId,
                                                      @Param("flashPromotionSessionId") Long flashPromotionSessionId);

    /**
     * 根据秒杀活动分类id查询，该分类下的所以场次，以及每个场次下的商品数量
     *
     * @param promotionId
     * @return
     */
    List<SeckillPromotionSessionDetail> getSessionListWithCount(Long promotionId);


    /**
     * 根据活动id查询该活动下有哪些时间段
     *
     * @param promotionId
     * @return
     */
    List<SeckillPromotionRelationProduct> getSessionListByPromotionId(Long promotionId);
}
