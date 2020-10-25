package com.young.db.dao;

import com.young.db.entity.YoungSearchHistory;
import com.young.db.entity.YoungSearchHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungSearchHistoryMapper {
    long countByExample(YoungSearchHistoryExample example);

    int deleteByExample(YoungSearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungSearchHistory record);

    int insertSelective(YoungSearchHistory record);

    List<YoungSearchHistory> selectByExample(YoungSearchHistoryExample example);

    YoungSearchHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungSearchHistory record, @Param("example") YoungSearchHistoryExample example);

    int updateByExample(@Param("record") YoungSearchHistory record, @Param("example") YoungSearchHistoryExample example);

    int updateByPrimaryKeySelective(YoungSearchHistory record);

    int updateByPrimaryKey(YoungSearchHistory record);
}