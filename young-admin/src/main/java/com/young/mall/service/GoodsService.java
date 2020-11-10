package com.young.mall.service;

import com.young.db.entity.YoungGoods;
import com.young.mall.dto.GoodsArguments;

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

    /**
     * 商品分类和品牌商
     * @return
     */
    Optional<Map> catAndBrand();

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    Optional<Map> detail(Integer id);

    /**
     * 更新商品
     * @param youngGoods
     * @return
     */
    Optional<Integer> update(YoungGoods youngGoods);

    /**
     * 删除商品
     * @param youngGoods
     * @return
     */
    Optional<Integer> delete(YoungGoods youngGoods);

    /**
     * 创建商品
     * @param goodsArguments
     * @return
     */
    Optional<Integer> create(GoodsArguments goodsArguments);
}
