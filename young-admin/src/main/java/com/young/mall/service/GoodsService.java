package com.young.mall.service;

import java.util.Optional;

/**
 * @Description: 商品 Service
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:46
 */
public interface GoodsService {

    /**
     * 查询 商品 数量
     * @return
     */
    Optional<Integer> count();
}
