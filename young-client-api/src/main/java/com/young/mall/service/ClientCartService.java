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
     * 根据用户id查询购物车选中状态的商品列表
     *
     * @param userId
     * @return
     */
    List<YoungCart> queryByUidAndChecked(Integer userId);

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

    /**
     * 购物车下单
     *
     * @param userId         用户ID
     * @param cartId         购物车商品ID： 如果购物车商品ID是空，则下单当前用户所有购物车商品； 如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId      收货地址ID： 如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId       优惠券ID： 如果优惠券ID是空，则自动选择合适的优惠券。
     * @param grouponRulesId
     * @return 购物车操作结果
     */
    ResBean checkOut(Integer userId, Integer cartId,
                     Integer addressId, Integer couponId,
                     Integer grouponRulesId);
}
