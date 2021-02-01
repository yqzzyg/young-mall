package com.young.db.dao;

import com.young.db.entity.YoungSeckillPromotionProductRelation;
import com.young.db.entity.YoungSeckillPromotionProductRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YoungSeckillPromotionProductRelationMapper {
    long countByExample(YoungSeckillPromotionProductRelationExample example);

    int deleteByExample(YoungSeckillPromotionProductRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YoungSeckillPromotionProductRelation record);

    int insertSelective(YoungSeckillPromotionProductRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_product_relation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionProductRelation selectOneByExample(YoungSeckillPromotionProductRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_product_relation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionProductRelation selectOneByExampleSelective(@Param("example") YoungSeckillPromotionProductRelationExample example, @Param("selective") YoungSeckillPromotionProductRelation.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_product_relation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungSeckillPromotionProductRelation> selectByExampleSelective(@Param("example") YoungSeckillPromotionProductRelationExample example, @Param("selective") YoungSeckillPromotionProductRelation.Column ... selective);

    List<YoungSeckillPromotionProductRelation> selectByExample(YoungSeckillPromotionProductRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_product_relation
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionProductRelation selectByPrimaryKeySelective(@Param("id") Long id, @Param("selective") YoungSeckillPromotionProductRelation.Column ... selective);

    YoungSeckillPromotionProductRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YoungSeckillPromotionProductRelation record, @Param("example") YoungSeckillPromotionProductRelationExample example);

    int updateByExample(@Param("record") YoungSeckillPromotionProductRelation record, @Param("example") YoungSeckillPromotionProductRelationExample example);

    int updateByPrimaryKeySelective(YoungSeckillPromotionProductRelation record);

    int updateByPrimaryKey(YoungSeckillPromotionProductRelation record);
}
