package com.young.db.dao;

import com.young.db.entity.YoungUserAccount;
import com.young.db.entity.YoungUserAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungUserAccountMapper {
    long countByExample(YoungUserAccountExample example);

    int deleteByExample(YoungUserAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungUserAccount record);

    int insertSelective(YoungUserAccount record);

    List<YoungUserAccount> selectByExample(YoungUserAccountExample example);

    YoungUserAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungUserAccount record, @Param("example") YoungUserAccountExample example);

    int updateByExample(@Param("record") YoungUserAccount record, @Param("example") YoungUserAccountExample example);

    int updateByPrimaryKeySelective(YoungUserAccount record);

    int updateByPrimaryKey(YoungUserAccount record);
}