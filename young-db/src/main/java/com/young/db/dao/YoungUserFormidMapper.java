package com.young.db.dao;

import com.young.db.entity.YoungUserFormid;
import com.young.db.entity.YoungUserFormidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungUserFormidMapper {
    long countByExample(YoungUserFormidExample example);

    int deleteByExample(YoungUserFormidExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungUserFormid record);

    int insertSelective(YoungUserFormid record);

    List<YoungUserFormid> selectByExample(YoungUserFormidExample example);

    YoungUserFormid selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungUserFormid record, @Param("example") YoungUserFormidExample example);

    int updateByExample(@Param("record") YoungUserFormid record, @Param("example") YoungUserFormidExample example);

    int updateByPrimaryKeySelective(YoungUserFormid record);

    int updateByPrimaryKey(YoungUserFormid record);
}