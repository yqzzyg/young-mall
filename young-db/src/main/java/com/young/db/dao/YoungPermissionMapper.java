package com.young.db.dao;

import com.young.db.entity.YoungPermission;
import com.young.db.entity.YoungPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungPermissionMapper {
    long countByExample(YoungPermissionExample example);

    int deleteByExample(YoungPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungPermission record);

    int insertSelective(YoungPermission record);

    List<YoungPermission> selectByExample(YoungPermissionExample example);

    YoungPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungPermission record, @Param("example") YoungPermissionExample example);

    int updateByExample(@Param("record") YoungPermission record, @Param("example") YoungPermissionExample example);

    int updateByPrimaryKeySelective(YoungPermission record);

    int updateByPrimaryKey(YoungPermission record);
}