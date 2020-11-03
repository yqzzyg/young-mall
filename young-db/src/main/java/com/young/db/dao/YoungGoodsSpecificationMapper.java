package com.young.db.dao;

import com.young.db.entity.YoungGoodsSpecification;
import com.young.db.entity.YoungGoodsSpecificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGoodsSpecificationMapper {
    long countByExample(YoungGoodsSpecificationExample example);

    int deleteByExample(YoungGoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGoodsSpecification record);

    int insertSelective(YoungGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGoodsSpecification selectOneByExample(YoungGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGoodsSpecification selectOneByExampleSelective(@Param("example") YoungGoodsSpecificationExample example, @Param("selective") YoungGoodsSpecification.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungGoodsSpecification> selectByExampleSelective(@Param("example") YoungGoodsSpecificationExample example, @Param("selective") YoungGoodsSpecification.Column ... selective);

    List<YoungGoodsSpecification> selectByExample(YoungGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGoodsSpecification selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungGoodsSpecification.Column ... selective);

    YoungGoodsSpecification selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGoodsSpecification selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungGoodsSpecification record, @Param("example") YoungGoodsSpecificationExample example);

    int updateByExample(@Param("record") YoungGoodsSpecification record, @Param("example") YoungGoodsSpecificationExample example);

    int updateByPrimaryKeySelective(YoungGoodsSpecification record);

    int updateByPrimaryKey(YoungGoodsSpecification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungGoodsSpecificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_goods_specification
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}