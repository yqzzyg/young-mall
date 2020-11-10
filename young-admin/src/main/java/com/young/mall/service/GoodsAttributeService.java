package com.young.mall.service;

import com.young.db.entity.YoungGoodsAttribute;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品参数
 * @Author: yqz
 * @CreateDate: 2020/11/10 16:01
 */
public interface GoodsAttributeService {

    /**
     * 根据goodsID查询分类
     * @param id
     * @return
     */
    Optional<List<YoungGoodsAttribute>> queryByGoodsId(Integer id);

    /**
     * 删除商品参数
     * @param gid 商品id
     * @return
     */
    Optional<Integer> delete(Integer gid);
}
