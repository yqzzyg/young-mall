package com.young.mall.service;

import java.util.Optional;

/**
 * @Description: 货品service
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:49
 */
public interface GoodsProductService {

    /**
     * 货品数量
     * @return
     */
    Optional<Integer> count();

    /**
     * 增加库存
     * @param id 产品ID
     * @param num 数量
     * @return
     */
    Optional<Integer> addStock(Integer id,Short num);
}
