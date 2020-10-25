package com.young.db.dao;

import com.young.db.entity.YoungAccountTrace;
import com.young.db.entity.YoungAccountTraceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAccountTraceMapper {
    long countByExample(YoungAccountTraceExample example);

    int deleteByExample(YoungAccountTraceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAccountTrace record);

    int insertSelective(YoungAccountTrace record);

    List<YoungAccountTrace> selectByExample(YoungAccountTraceExample example);

    YoungAccountTrace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAccountTrace record, @Param("example") YoungAccountTraceExample example);

    int updateByExample(@Param("record") YoungAccountTrace record, @Param("example") YoungAccountTraceExample example);

    int updateByPrimaryKeySelective(YoungAccountTrace record);

    int updateByPrimaryKey(YoungAccountTrace record);
}