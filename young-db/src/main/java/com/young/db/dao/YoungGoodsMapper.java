package com.young.db.dao;

import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoungGoodsMapper {
    long countByExample(YoungGoodsExample example);

    int deleteByExample(YoungGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YoungGoods record);

    int insertSelective(YoungGoods record);

    List<YoungGoods> selectByExampleWithBLOBs(YoungGoodsExample example);

    List<YoungGoods> selectByExample(YoungGoodsExample example);

    YoungGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YoungGoods record, @Param("example") YoungGoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") YoungGoods record, @Param("example") YoungGoodsExample example);

    int updateByExample(@Param("record") YoungGoods record, @Param("example") YoungGoodsExample example);

    int updateByPrimaryKeySelective(YoungGoods record);

    int updateByPrimaryKeyWithBLOBs(YoungGoods record);

    int updateByPrimaryKey(YoungGoods record);
}