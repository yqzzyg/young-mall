package com.young.db.dao;

import com.young.db.entity.YoungSeckillPromotionLog;
import com.young.db.entity.YoungSeckillPromotionLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YoungSeckillPromotionLogMapper {
    long countByExample(YoungSeckillPromotionLogExample example);

    int deleteByExample(YoungSeckillPromotionLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungSeckillPromotionLog record);

    int insertSelective(YoungSeckillPromotionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionLog selectOneByExample(YoungSeckillPromotionLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionLog selectOneByExampleSelective(@Param("example") YoungSeckillPromotionLogExample example, @Param("selective") YoungSeckillPromotionLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungSeckillPromotionLog> selectByExampleSelective(@Param("example") YoungSeckillPromotionLogExample example, @Param("selective") YoungSeckillPromotionLog.Column ... selective);

    List<YoungSeckillPromotionLog> selectByExample(YoungSeckillPromotionLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungSeckillPromotionLog.Column ... selective);

    YoungSeckillPromotionLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungSeckillPromotionLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungSeckillPromotionLog record, @Param("example") YoungSeckillPromotionLogExample example);

    int updateByExample(@Param("record") YoungSeckillPromotionLog record, @Param("example") YoungSeckillPromotionLogExample example);

    int updateByPrimaryKeySelective(YoungSeckillPromotionLog record);

    int updateByPrimaryKey(YoungSeckillPromotionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungSeckillPromotionLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_seckill_promotion_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}
