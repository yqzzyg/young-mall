package com.young.mall.service;

import com.young.db.entity.YoungGoods;

import java.util.List;
import java.util.Map;
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

    /**
     * 分页查询
     * @param goodsSn 商品编号
     * @param name
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    Optional<List<YoungGoods>> querySelective(String goodsSn, String name,
                                              Integer page, Integer size,
                                              String sort, String order);

    Optional<Map> catAndBrand();
}
