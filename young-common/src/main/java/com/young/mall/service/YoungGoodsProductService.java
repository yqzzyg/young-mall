package com.young.mall.service;

import com.young.db.entity.YoungGoodsProduct;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品货品、库存业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 16:33
 */
public interface YoungGoodsProductService {

    /**
     * 根据商品ID查询商品货品
     * @param id
     * @return
     */
    Optional<List<YoungGoodsProduct>> queryByGoodsId(Integer id);

    /**
     * 删除货品、库存
     * @param gid
     * @return
     */
    Optional<Integer> deleteByGid(Integer gid);
}
