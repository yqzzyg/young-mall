package com.young.db.dao;

import com.young.db.entity.YoungAddress;
import com.young.db.entity.YoungAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungAddressMapper {
    long countByExample(YoungAddressExample example);

    int deleteByExample(YoungAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungAddress record);

    int insertSelective(YoungAddress record);

    List<YoungAddress> selectByExample(YoungAddressExample example);

    YoungAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungAddress record, @Param("example") YoungAddressExample example);

    int updateByExample(@Param("record") YoungAddress record, @Param("example") YoungAddressExample example);

    int updateByPrimaryKeySelective(YoungAddress record);

    int updateByPrimaryKey(YoungAddress record);
}