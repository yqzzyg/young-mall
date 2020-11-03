package com.young.db.mapper;

import org.apache.ibatis.annotations.Param;

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

}
