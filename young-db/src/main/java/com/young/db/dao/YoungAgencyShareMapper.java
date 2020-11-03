package com.young.db.dao;

import com.young.db.entity.YoungAgencyShare;
import com.young.db.entity.YoungAgencyShareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAgencyShareMapper {
    long countByExample(YoungAgencyShareExample example);

    int deleteByExample(YoungAgencyShareExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAgencyShare record);

    int insertSelective(YoungAgencyShare record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_agency_share
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungAgencyShare selectOneByExample(YoungAgencyShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_agency_share
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungAgencyShare selectOneByExampleSelective(@Param("example") YoungAgencyShareExample example, @Param("selective") YoungAgencyShare.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_agency_share
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungAgencyShare> selectByExampleSelective(@Param("example") YoungAgencyShareExample example, @Param("selective") YoungAgencyShare.Column ... selective);

    List<YoungAgencyShare> selectByExample(YoungAgencyShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_agency_share
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungAgencyShare selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungAgencyShare.Column ... selective);

    YoungAgencyShare selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAgencyShare record, @Param("example") YoungAgencyShareExample example);

    int updateByExample(@Param("record") YoungAgencyShare record, @Param("example") YoungAgencyShareExample example);

    int updateByPrimaryKeySelective(YoungAgencyShare record);

    int updateByPrimaryKey(YoungAgencyShare record);
}