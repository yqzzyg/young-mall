package com.young.db.dao;

import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCategoryMapper {
    long countByExample(YoungCategoryExample example);

    int deleteByExample(YoungCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungCategory record);

    int insertSelective(YoungCategory record);

    List<YoungCategory> selectByExample(YoungCategoryExample example);

    YoungCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungCategory record, @Param("example") YoungCategoryExample example);

    int updateByExample(@Param("record") YoungCategory record, @Param("example") YoungCategoryExample example);

    int updateByPrimaryKeySelective(YoungCategory record);

    int updateByPrimaryKey(YoungCategory record);
}