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
     *
     * @param id
     * @param num
     * @return
     */
    int addStock(@Param("id") Integer id, @Param("num") Short num);

    /**
     * 批量插入商品价格、库存相关信息
     *
     * @param list
     * @return
     */
    Integer insertList(@Param("list") List<YoungGoodsProduct> list);

    /**
     * 减库存
     *
     * @param id      young_goods_product主键id
     * @param num     需要减的数量
     * @param version 乐观锁版本号
     * @return
     */
    int reduceStock(@Param("id") Integer id, @Param("num") Short num, @Param("version") Integer version);

    /**
     * @param id young_goods主键id
     * @param num 添加的数量
     * @return
     */
    int addSales(@Param("id") Integer id, @Param("num") Short num);
}
