package com.young.db.dao;

import com.young.db.entity.YoungSeckillPromotionSession;
import com.young.db.entity.YoungSeckillPromotionSessionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YoungSeckillPromotionSessionMapper {
    long countByExample(YoungSeckillPromotionSessionExample example);

    int deleteByExample(YoungSeckillPromotionSessionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YoungSeckillPromotionSession record);

    int insertSelective(YoungSeckillPromotionSession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionSession selectOneByExample(YoungSeckillPromotionSessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionSession selectOneByExampleSelective(@Param("example") YoungSeckillPromotionSessionExample example, @Param("selective") YoungSeckillPromotionSession.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungSeckillPromotionSession> selectByExampleSelective(@Param("example") YoungSeckillPromotionSessionExample example, @Param("selective") YoungSeckillPromotionSession.Column ... selective);

    List<YoungSeckillPromotionSession> selectByExample(YoungSeckillPromotionSessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionSession selectByPrimaryKeySelective(@Param("id") Long id, @Param("selective") YoungSeckillPromotionSession.Column ... selective);

    YoungSeckillPromotionSession selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionSession selectByPrimaryKeyWithLogicalDelete(@Param("id") Long id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungSeckillPromotionSession record, @Param("example") YoungSeckillPromotionSessionExample example);

    int updateByExample(@Param("record") YoungSeckillPromotionSession record, @Param("example") YoungSeckillPromotionSessionExample example);

    int updateByPrimaryKeySelective(YoungSeckillPromotionSession record);

    int updateByPrimaryKey(YoungSeckillPromotionSession record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungSeckillPromotionSessionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_session
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Long id);
}