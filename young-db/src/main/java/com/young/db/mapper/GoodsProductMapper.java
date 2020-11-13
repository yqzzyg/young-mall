package com.young.db.mapper;

import com.young.db.entity.YoungGoodsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 16:41
 */
public interface GoodsProductMapper {
    /**
     * 更改数据库库存
     * @param id
     * @param num
     * @return
     */
    int addStock(@Param("id") Integer id, @Param("num") Short num);

    /**
     * 批量插入商品价格、库存相关信息
     * @param list
     * @return
     */
    Integer insertList(@Param("list")List<YoungGoodsProduct> list);
}
