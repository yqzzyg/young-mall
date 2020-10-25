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

    List<YoungArticle> selectByExampleWithBLOBs(YoungArticleExample example);

    List<YoungArticle> selectByExample(YoungArticleExample example);

    YoungArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByExample(@Param("record") YoungArticle record, @Param("example") YoungArticleExample example);

    int updateByPrimaryKeySelective(YoungArticle record);

    int updateByPrimaryKeyWithBLOBs(YoungArticle record);

    int updateByPrimaryKey(YoungArticle record);
}