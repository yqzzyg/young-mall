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

    /**
     * 分页查询商品
     *
     * @param catId    分类id
     * @param brandId  品牌id
     * @param keywords 关键词
     * @param isHot    是否热品
     * @param isNew    是否新品
     * @param page     起始页
     * @param limit    每页大小
     * @param sort     排序依据
     * @param order    排序方式
     * @return
     */
    List<YoungGoods> querySelective(Integer catId, Integer brandId,
                                    String keywords,
                                    Boolean isHot, Boolean isNew,
                                    Integer page, Integer limit, String sort, String order);

    /**
     * 查询商品所属类目列表
     *
     * @param brandId  品牌id
     * @param keywords 关键词
     * @param isHot
     * @param isNew
     * @return
     */
    List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew);
}
