package com.young.db.dao;

import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAdMapper {
    long countByExample(YoungAdExample example);

    int deleteByExample(YoungAdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAd record);

    int insertSelective(YoungAd record);

    List<YoungAd> selectByExample(YoungAdExample example);

    YoungAd selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAd record, @Param("example") YoungAdExample example);

    int updateByExample(@Param("record") YoungAd record, @Param("example") YoungAdExample example);

    int updateByPrimaryKeySelective(YoungAd record);

    int updateByPrimaryKey(YoungAd record);
}