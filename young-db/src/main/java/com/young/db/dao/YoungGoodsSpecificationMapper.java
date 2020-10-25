package com.young.db.dao;

import com.young.db.entity.YoungGoodsSpecification;
import com.young.db.entity.YoungGoodsSpecificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGoodsSpecificationMapper {
    long countByExample(YoungGoodsSpecificationExample example);

    int deleteByExample(YoungGoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGoodsSpecification record);

    int insertSelective(YoungGoodsSpecification record);

    List<YoungGoodsSpecification> selectByExample(YoungGoodsSpecificationExample example);

    YoungGoodsSpecification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGoodsSpecification record, @Param("example") YoungGoodsSpecificationExample example);

    int updateByExample(@Param("record") YoungGoodsSpecification record, @Param("example") YoungGoodsSpecificationExample example);

    int updateByPrimaryKeySelective(YoungGoodsSpecification record);

    int updateByPrimaryKey(YoungGoodsSpecification record);
}