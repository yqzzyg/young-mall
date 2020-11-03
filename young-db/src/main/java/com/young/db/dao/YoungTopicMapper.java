package com.young.db.dao;

import com.young.db.entity.YoungTopic;
import com.young.db.entity.YoungTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungTopicMapper {
    long countByExample(YoungTopicExample example);

    int deleteByExample(YoungTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungTopic record);

    int insertSelective(YoungTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungTopic selectOneByExample(YoungTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungTopic selectOneByExampleSelective(@Param("example") YoungTopicExample example, @Param("selective") YoungTopic.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungTopic selectOneByExampleWithBLOBs(YoungTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungTopic> selectByExampleSelective(@Param("example") YoungTopicExample example, @Param("selective") YoungTopic.Column ... selective);

    List<YoungTopic> selectByExampleWithBLOBs(YoungTopicExample example);

    List<YoungTopic> selectByExample(YoungTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungTopic selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungTopic.Column ... selective);

    YoungTopic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungTopic selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByExample(@Param("record") YoungTopic record, @Param("example") YoungTopicExample example);

    int updateByPrimaryKeySelective(YoungTopic record);

    int updateByPrimaryKeyWithBLOBs(YoungTopic record);

    int updateByPrimaryKey(YoungTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_topic
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}