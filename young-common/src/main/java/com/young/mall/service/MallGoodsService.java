package com.young.mall.service;

import com.young.db.entity.YoungGoods;

import java.util.Optional;

/**
 * @Description: 商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:37
 */
public interface MallGoodsService {

    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    Optional<YoungGoods> findById(Integer id);

    /**
     * 根据商品名称校验是否已经存在
     * @param name 商品名
     * @return
     */
    Boolean checkExistByName(String name);

    /**
     * 根据id更新商品
     * @param youngGoods
     * @return
     */
    Integer updateById(YoungGoods youngGoods);
}
