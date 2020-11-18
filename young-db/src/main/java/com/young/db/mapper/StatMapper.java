package com.young.db.mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: 统计
 * @Author: yqz
 * @CreateDate: 2020/11/18 22:02
 */
public interface StatMapper {
    /**
     * 用户统计
     *
     * @return
     */
    List<Map> statUser();

    /**
     * 订单统计
     *
     * @return
     */
    List<Map> statOrder();

    /**
     * 商品统计
     *
     * @return
     */
    List<Map> statGoods();
}
