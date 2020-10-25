package com.young.db.dao;

import com.young.db.entity.YoungCart;
import com.young.db.entity.YoungCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungCartMapper {
    long countByExample(YoungCartExample example);

    int deleteByExample(YoungCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungCart record);

    int insertSelective(YoungCart record);

    List<YoungCart> selectByExample(YoungCartExample example);

    YoungCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungCart record, @Param("example") YoungCartExample example);

    int updateByExample(@Param("record") YoungCart record, @Param("example") YoungCartExample example);

    int updateByPrimaryKeySelective(YoungCart record);

    int updateByPrimaryKey(YoungCart record);
}