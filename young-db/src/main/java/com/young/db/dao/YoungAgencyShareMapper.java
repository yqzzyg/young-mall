package com.young.db.dao;

import com.young.db.entity.YoungAgencyShare;
import com.young.db.entity.YoungAgencyShareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAgencyShareMapper {
    long countByExample(YoungAgencyShareExample example);

    int deleteByExample(YoungAgencyShareExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAgencyShare record);

    int insertSelective(YoungAgencyShare record);

    List<YoungAgencyShare> selectByExample(YoungAgencyShareExample example);

    YoungAgencyShare selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAgencyShare record, @Param("example") YoungAgencyShareExample example);

    int updateByExample(@Param("record") YoungAgencyShare record, @Param("example") YoungAgencyShareExample example);

    int updateByPrimaryKeySelective(YoungAgencyShare record);

    int updateByPrimaryKey(YoungAgencyShare record);
}