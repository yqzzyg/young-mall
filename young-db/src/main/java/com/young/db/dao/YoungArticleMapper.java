package com.young.db.dao;

import com.young.db.entity.YoungArticle;
import com.young.db.entity.YoungArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungArticleMapper {
    long countByExample(YoungArticleExample example);

    int deleteByExample(YoungArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungArticle record);

    int insertSelective(YoungArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungArticle selectOneByExample(YoungArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungArticle selectOneByExampleSelective(@Param("example") YoungArticleExample example, @Param("selective") YoungArticle.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungArticle selectOneByExampleWithBLOBs(YoungArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<YoungArticle> selectByExampleSelective(@Param("example") YoungArticleExample example, @Param("selective") YoungArticle.Column ... selective);

    List<YoungArticle> selectByExampleWithBLOBs(YoungArticleExample example);

    List<YoungArticle> selectByExample(YoungArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungArticle selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") YoungArticle.Column ... selective);

    YoungArticle selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    YoungArticle selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByExample(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByPrimaryKeySelective(YoungArticle record);

    int updateByPrimaryKeyWithBLOBs(YoungArticle record);

    int updateByPrimaryKey(YoungArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") YoungArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table young_article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}