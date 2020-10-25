package com.young.db.dao;

import com.young.db.entity.YoungGrouponRules;
import com.young.db.entity.YoungGrouponRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGrouponRulesMapper {
    long countByExample(YoungGrouponRulesExample example);

    int deleteByExample(YoungGrouponRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGrouponRules record);

    int insertSelective(YoungGrouponRules record);

    List<YoungGrouponRules> selectByExample(YoungGrouponRulesExample example);

    YoungGrouponRules selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGrouponRules record, @Param("example") YoungGrouponRulesExample example);

    int updateByExample(@Param("record") YoungGrouponRules record, @Param("example") YoungGrouponRulesExample example);

    int updateByPrimaryKeySelective(YoungGrouponRules record);

    int updateByPrimaryKey(YoungGrouponRules record);
}