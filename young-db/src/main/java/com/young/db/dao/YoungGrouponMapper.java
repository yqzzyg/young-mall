package com.young.db.dao;

import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungGrouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGrouponMapper {
    long countByExample(YoungGrouponExample example);

    int deleteByExample(YoungGrouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGroupon record);

    int insertSelective(YoungGroupon record);

    List<YoungGroupon> selectByExample(YoungGrouponExample example);

    YoungGroupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGroupon record, @Param("example") YoungGrouponExample example);

    int updateByExample(@Param("record") YoungGroupon record, @Param("example") YoungGrouponExample example);

    int updateByPrimaryKeySelective(YoungGroupon record);

    int updateByPrimaryKey(YoungGroupon record);
}