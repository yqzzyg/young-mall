package com.young.db.dao;

import com.young.db.entity.YoungGrouponRules;
import com.young.db.entity.YoungGrouponRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGrouponRulesMapper {
    long countByExample(YoungGrouponRulesExample example);

    int deleteByExample(YoungGrouponRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGrouponRules record);

    int insertSelective(YoungGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGrouponRules selectOneByExample(YoungGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGrouponRules selectOneByExampleSelective(@Param("example") YoungGrouponRulesExample example, @Param("selective") YoungGrouponRules.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungGrouponRules> selectByExampleSelective(@Param("example") YoungGrouponRulesExample example, @Param("selective") YoungGrouponRules.Column ... selective);

    List<YoungGrouponRules> selectByExample(YoungGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGrouponRules selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungGrouponRules.Column ... selective);

    YoungGrouponRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungGrouponRules selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungGrouponRules record, @Param("example") YoungGrouponRulesExample example);

    int updateByExample(@Param("record") YoungGrouponRules record, @Param("example") YoungGrouponRulesExample example);

    int updateByPrimaryKeySelective(YoungGrouponRules record);

    int updateByPrimaryKey(YoungGrouponRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungGrouponRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_groupon_rules
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}