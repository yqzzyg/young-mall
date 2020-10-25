package com.young.db.dao;

import com.young.db.entity.YoungBrand;
import com.young.db.entity.YoungBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungBrandMapper {
    long countByExample(YoungBrandExample example);

    int deleteByExample(YoungBrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungBrand record);

    int insertSelective(YoungBrand record);

    List<YoungBrand> selectByExample(YoungBrandExample example);

    YoungBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungBrand record, @Param("example") YoungBrandExample example);

    int updateByExample(@Param("record") YoungBrand record, @Param("example") YoungBrandExample example);

    int updateByPrimaryKeySelective(YoungBrand record);

    int updateByPrimaryKey(YoungBrand record);
}