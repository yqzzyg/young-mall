package com.young.db.dao;

import com.young.db.entity.YoungFootprint;
import com.young.db.entity.YoungFootprintExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungFootprintMapper {
    long countByExample(YoungFootprintExample example);

    int deleteByExample(YoungFootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungFootprint record);

    int insertSelective(YoungFootprint record);

    List<YoungFootprint> selectByExample(YoungFootprintExample example);

    YoungFootprint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungFootprint record, @Param("example") YoungFootprintExample example);

    int updateByExample(@Param("record") YoungFootprint record, @Param("example") YoungFootprintExample example);

    int updateByPrimaryKeySelective(YoungFootprint record);

    int updateByPrimaryKey(YoungFootprint record);
}