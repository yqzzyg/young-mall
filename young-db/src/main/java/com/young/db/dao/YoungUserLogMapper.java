package com.young.db.dao;

import com.young.db.entity.YoungUserLog;
import com.young.db.entity.YoungUserLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YoungUserLogMapper {
    long countByExample(YoungUserLogExample example);

    int deleteByExample(YoungUserLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungUserLog record);

    int insertSelective(YoungUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserLog selectOneByExample(YoungUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserLog selectOneByExampleSelective(@Param("example") YoungUserLogExample example, @Param("selective") YoungUserLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserLog selectOneByExampleWithBLOBs(YoungUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungUserLog> selectByExampleSelective(@Param("example") YoungUserLogExample example, @Param("selective") YoungUserLog.Column ... selective);

    List<YoungUserLog> selectByExampleWithBLOBs(YoungUserLogExample example);

    List<YoungUserLog> selectByExample(YoungUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungUserLog.Column ... selective);

    YoungUserLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungUserLog record, @Param("example") YoungUserLogExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungUserLog record, @Param("example") YoungUserLogExample example);

    int updateByExample(@Param("record") YoungUserLog record, @Param("example") YoungUserLogExample example);

    int updateByPrimaryKeySelective(YoungUserLog record);

    int updateByPrimaryKeyWithBLOBs(YoungUserLog record);

    int updateByPrimaryKey(YoungUserLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungUserLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_log
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}
