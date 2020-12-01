package com.young.mall.service;

import com.young.db.entity.YoungGoods;

import java.util.List;
import java.util.Map;

/**
 * @Description: 小程序端
 * @Author: yqz
 * @CreateDate: 2020/11/22 11:28
 */
public interface ClientGoodsService {


    /**
     * 获取新品
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungGoods> queryByNew(int page, int size);

    /**
     * 获取新品上市
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungGoods> queryByHot(int page, int size);

    /**
     * 根据商品分类 id 查询该分类下的商品
     *
     * @param cid
     * @param page
     * @param size
     * @return
     */
    List<YoungGoods> getGoodByCategoryId(List<Integer> cid, int page, int size);

    /**
     * 查询在售商品的数量
     *
     * @return
     */
    Integer getGoodsCountOnSale();

    /**
     * 通过商品类目id查询商品类目
     *
     * @param id
     * @return
     */
    Map<String, Object> getCategoryById(Integer id);
}
