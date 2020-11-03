package com.young.db.dao;

import com.young.db.entity.YoungUserAccount;
import com.young.db.entity.YoungUserAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungUserAccountMapper {
    long countByExample(YoungUserAccountExample example);

    int deleteByExample(YoungUserAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungUserAccount record);

    int insertSelective(YoungUserAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_account
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserAccount selectOneByExample(YoungUserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_account
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserAccount selectOneByExampleSelective(@Param("example") YoungUserAccountExample example, @Param("selective") YoungUserAccount.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_account
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungUserAccount> selectByExampleSelective(@Param("example") YoungUserAccountExample example, @Param("selective") YoungUserAccount.Column ... selective);

    List<YoungUserAccount> selectByExample(YoungUserAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_user_account
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungUserAccount selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungUserAccount.Column ... selective);

    YoungUserAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungUserAccount record, @Param("example") YoungUserAccountExample example);

    int updateByExample(@Param("record") YoungUserAccount record, @Param("example") YoungUserAccountExample example);

    int updateByPrimaryKeySelective(YoungUserAccount record);

    int updateByPrimaryKey(YoungUserAccount record);
}