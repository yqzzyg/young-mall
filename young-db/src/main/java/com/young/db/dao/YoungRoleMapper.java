package com.young.db.dao;

import com.young.db.entity.YoungRole;
import com.young.db.entity.YoungRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungRoleMapper {
    long countByExample(YoungRoleExample example);

    int deleteByExample(YoungRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungRole record);

    int insertSelective(YoungRole record);

    List<YoungRole> selectByExample(YoungRoleExample example);

    YoungRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungRole record, @Param("example") YoungRoleExample example);

    int updateByExample(@Param("record") YoungRole record, @Param("example") YoungRoleExample example);

    int updateByPrimaryKeySelective(YoungRole record);

    int updateByPrimaryKey(YoungRole record);
}