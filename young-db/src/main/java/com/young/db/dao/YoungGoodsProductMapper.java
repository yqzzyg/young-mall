package com.young.db.dao;

import com.young.db.entity.YoungGoodsProduct;
import com.young.db.entity.YoungGoodsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGoodsProductMapper {
    long countByExample(YoungGoodsProductExample example);

    int deleteByExample(YoungGoodsProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGoodsProduct record);

    int insertSelective(YoungGoodsProduct record);

    List<YoungGoodsProduct> selectByExample(YoungGoodsProductExample example);

    YoungGoodsProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGoodsProduct record, @Param("example") YoungGoodsProductExample example);

    int updateByExample(@Param("record") YoungGoodsProduct record, @Param("example") YoungGoodsProductExample example);

    int updateByPrimaryKeySelective(YoungGoodsProduct record);

    int updateByPrimaryKey(YoungGoodsProduct record);
}