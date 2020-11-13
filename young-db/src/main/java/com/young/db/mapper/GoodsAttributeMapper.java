package com.young.db.mapper;

import com.young.db.entity.YoungGoodsAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 商品参数操作
 * @Author: yqz
 * @CreateDate: 2020/11/13 14:58
 */
public interface GoodsAttributeMapper {

    /**
     * 批量插入商品参数
     * @param list
     * @return
     */
    Integer insertList(@Param("list") List<YoungGoodsAttribute> list);
}
