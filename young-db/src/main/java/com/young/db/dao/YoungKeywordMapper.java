package com.young.db.dao;

import com.young.db.entity.YoungKeyword;
import com.young.db.entity.YoungKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungKeywordMapper {
    long countByExample(YoungKeywordExample example);

    int deleteByExample(YoungKeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungKeyword record);

    int insertSelective(YoungKeyword record);

    List<YoungKeyword> selectByExample(YoungKeywordExample example);

    YoungKeyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungKeyword record, @Param("example") YoungKeywordExample example);

    int updateByExample(@Param("record") YoungKeyword record, @Param("example") YoungKeywordExample example);

    int updateByPrimaryKeySelective(YoungKeyword record);

    int updateByPrimaryKey(YoungKeyword record);
}