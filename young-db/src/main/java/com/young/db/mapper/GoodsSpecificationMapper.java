package com.young.db.mapper;

import com.young.db.entity.YoungGoodsSpecification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 商品规格自定义mapper
 * @Author: yqz
 * @CreateDate: 2020/11/13 13:38
 */
@Mapper
public interface GoodsSpecificationMapper {

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    Integer insertList(@Param("list") List<YoungGoodsSpecification> list);
}
