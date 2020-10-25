package com.young.db.dao;

import com.young.db.entity.YoungAdmin;
import com.young.db.entity.YoungAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAdminMapper {
    long countByExample(YoungAdminExample example);

    int deleteByExample(YoungAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAdmin record);

    int insertSelective(YoungAdmin record);

    List<YoungAdmin> selectByExample(YoungAdminExample example);

    YoungAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAdmin record, @Param("example") YoungAdminExample example);

    int updateByExample(@Param("record") YoungAdmin record, @Param("example") YoungAdminExample example);

    int updateByPrimaryKeySelective(YoungAdmin record);

    int updateByPrimaryKey(YoungAdmin record);
}