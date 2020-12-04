package com.young.db.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 自定义sql操作young_goods
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:55
 */
public interface GoodsMapper {

    /**
     * 添加浏览记录
     *
     * @param id
     * @param num
     * @return
     */
    int addBrowse(@Param("id") Integer id, @Param("num") Short num);

}
