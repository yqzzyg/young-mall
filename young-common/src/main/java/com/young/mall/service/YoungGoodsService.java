package com.young.mall.service;

import com.young.db.entity.YoungGoods;

import java.util.Optional;

/**
 * @Description: 商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:37
 */
public interface YoungGoodsService {

    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    Optional<YoungGoods> findById(Integer id);
}
