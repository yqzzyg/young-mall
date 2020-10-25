package com.young.db.dao;

import com.young.db.entity.YoungOrder;
import com.young.db.entity.YoungOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungOrderMapper {
    long countByExample(YoungOrderExample example);

    int deleteByExample(YoungOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungOrder record);

    int insertSelective(YoungOrder record);

    List<YoungOrder> selectByExample(YoungOrderExample example);

    YoungOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungOrder record, @Param("example") YoungOrderExample example);

    int updateByExample(@Param("record") YoungOrder record, @Param("example") YoungOrderExample example);

    int updateByPrimaryKeySelective(YoungOrder record);

    int updateByPrimaryKey(YoungOrder record);
}