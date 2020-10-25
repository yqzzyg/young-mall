package com.young.db.dao;

import com.young.db.entity.YoungUser;
import com.young.db.entity.YoungUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungUserMapper {
    long countByExample(YoungUserExample example);

    int deleteByExample(YoungUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungUser record);

    int insertSelective(YoungUser record);

    List<YoungUser> selectByExample(YoungUserExample example);

    YoungUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungUser record, @Param("example") YoungUserExample example);

    int updateByExample(@Param("record") YoungUser record, @Param("example") YoungUserExample example);

    int updateByPrimaryKeySelective(YoungUser record);

    int updateByPrimaryKey(YoungUser record);
}