package com.young.db.dao;

import com.young.db.entity.YoungSystem;
import com.young.db.entity.YoungSystemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungSystemMapper {
    long countByExample(YoungSystemExample example);

    int deleteByExample(YoungSystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungSystem record);

    int insertSelective(YoungSystem record);

    List<YoungSystem> selectByExample(YoungSystemExample example);

    YoungSystem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungSystem record, @Param("example") YoungSystemExample example);

    int updateByExample(@Param("record") YoungSystem record, @Param("example") YoungSystemExample example);

    int updateByPrimaryKeySelective(YoungSystem record);

    int updateByPrimaryKey(YoungSystem record);
}