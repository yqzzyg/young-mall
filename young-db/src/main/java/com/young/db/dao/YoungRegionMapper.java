package com.young.db.dao;

import com.young.db.entity.YoungRegion;
import com.young.db.entity.YoungRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungRegionMapper {
    long countByExample(YoungRegionExample example);

    int deleteByExample(YoungRegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungRegion record);

    int insertSelective(YoungRegion record);

    List<YoungRegion> selectByExample(YoungRegionExample example);

    YoungRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungRegion record, @Param("example") YoungRegionExample example);

    int updateByExample(@Param("record") YoungRegion record, @Param("example") YoungRegionExample example);

    int updateByPrimaryKeySelective(YoungRegion record);

    int updateByPrimaryKey(YoungRegion record);
}