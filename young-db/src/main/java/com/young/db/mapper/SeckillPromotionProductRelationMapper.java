package com.young.db.mapper;

import com.young.db.pojo.SeckillPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 自定义限时购商品关系管理Dao
 * @Author: yqz
 * @CreateDate: 2021/2/1 15:30
 */
public interface SeckillPromotionProductRelationMapper {

    /**
     * 获取限时购及相关商品信息
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @return
     */
    List<SeckillPromotionProduct> getSeckillGoodsList(@Param("flashPromotionId") Long flashPromotionId,
                                          @Param("flashPromotionSessionId") Long flashPromotionSessionId);

}
