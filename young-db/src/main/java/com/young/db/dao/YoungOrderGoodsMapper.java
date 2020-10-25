package com.young.db.dao;

import com.young.db.entity.YoungOrderGoods;
import com.young.db.entity.YoungOrderGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungOrderGoodsMapper {
    long countByExample(YoungOrderGoodsExample example);

    int deleteByExample(YoungOrderGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungOrderGoods record);

    int insertSelective(YoungOrderGoods record);

    List<YoungOrderGoods> selectByExample(YoungOrderGoodsExample example);

    YoungOrderGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungOrderGoods record, @Param("example") YoungOrderGoodsExample example);

    int updateByExample(@Param("record") YoungOrderGoods record, @Param("example") YoungOrderGoodsExample example);

    int updateByPrimaryKeySelective(YoungOrderGoods record);

    int updateByPrimaryKey(YoungOrderGoods record);
}