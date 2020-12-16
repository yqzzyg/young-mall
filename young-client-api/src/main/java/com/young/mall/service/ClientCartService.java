package com.young.mall.service;

import com.young.db.entity.YoungCart;
import com.young.mall.common.ResBean;

import java.util.List;
import java.util.Map;

/**
 * @Description: 购物车业务
 * @Author: yqz
 * @CreateDate: 2020/11/28 16:52
 */
public interface ClientCartService {

    /**
     * 根据用户id查询购物车
     *
     * @param uid 用户主键id
     * @return
     */
    List<YoungCart> queryByUid(Integer uid);

    /**
     * 购物车首页
     *
     * @param uid 用户主键id
     * @return
     */
    ResBean<Map<String, Object>> index(Integer uid);

    /**
     * 根据商品id、商品货品表的货品id、用户id查询购物车
     *
     * @param goodsId
     * @param productId
     * @param userId
     * @return
     */
    YoungCart queryExist(Integer goodsId, Integer productId, Integer userId);

    /**
     * 添加购物车
     *
     * @param cart
     * @return
     */
    Integer add(YoungCart cart);

    /**
     * 根据购物车id，更新购物车
     *
     * @param cart
     * @return
     */
    Integer updateById(YoungCart cart);

    /**
     * 更新购物车商品选中状态
     *
     * @param userId
     * @param idsList
     * @param checked
     * @return
     */
    Integer updateCheck(Integer userId, List<Integer> idsList, boolean checked);

    /**
     * 通过
     *
     * @param id 购物车id
     * @return
     */
    YoungCart findById(Integer id);

    /**
     * 删除购物车
     *
     * @param productIdList 商品货品表的货品ID
     * @param userId        用户id
     * @return 删除个数
     */
    Integer delete(List<Integer> productIdList, int userId);
}
