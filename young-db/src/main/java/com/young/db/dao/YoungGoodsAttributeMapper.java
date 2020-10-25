package com.young.db.dao;

import com.young.db.entity.YoungGoodsAttribute;
import com.young.db.entity.YoungGoodsAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGoodsAttributeMapper {
    long countByExample(YoungGoodsAttributeExample example);

    int deleteByExample(YoungGoodsAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGoodsAttribute record);

    int insertSelective(YoungGoodsAttribute record);

    List<YoungGoodsAttribute> selectByExample(YoungGoodsAttributeExample example);

    YoungGoodsAttribute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGoodsAttribute record, @Param("example") YoungGoodsAttributeExample example);

    int updateByExample(@Param("record") YoungGoodsAttribute record, @Param("example") YoungGoodsAttributeExample example);

    int updateByPrimaryKeySelective(YoungGoodsAttribute record);

    int updateByPrimaryKey(YoungGoodsAttribute record);
}