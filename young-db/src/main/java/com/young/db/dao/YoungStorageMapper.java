package com.young.db.dao;

import com.young.db.entity.YoungStorage;
import com.young.db.entity.YoungStorageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungStorageMapper {
    long countByExample(YoungStorageExample example);

    int deleteByExample(YoungStorageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungStorage record);

    int insertSelective(YoungStorage record);

    List<YoungStorage> selectByExample(YoungStorageExample example);

    YoungStorage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungStorage record, @Param("example") YoungStorageExample example);

    int updateByExample(@Param("record") YoungStorage record, @Param("example") YoungStorageExample example);

    int updateByPrimaryKeySelective(YoungStorage record);

    int updateByPrimaryKey(YoungStorage record);
}