package com.young.db.dao;

import com.young.db.entity.YoungCollect;
import com.young.db.entity.YoungCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCollectMapper {
    long countByExample(YoungCollectExample example);

    int deleteByExample(YoungCollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungCollect record);

    int insertSelective(YoungCollect record);

    List<YoungCollect> selectByExample(YoungCollectExample example);

    YoungCollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungCollect record, @Param("example") YoungCollectExample example);

    int updateByExample(@Param("record") YoungCollect record, @Param("example") YoungCollectExample example);

    int updateByPrimaryKeySelective(YoungCollect record);

    int updateByPrimaryKey(YoungCollect record);
}