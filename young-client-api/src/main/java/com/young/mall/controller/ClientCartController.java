package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungCart;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsProduct;
import com.young.mall.common.ResBean;
import com.young.mall.domain.CartCheckDto;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.enums.ClientResponseCode;
import com.young.mall.domain.vo.FastAddVo;
import com.young.mall.service.ClientCartService;
import com.young.mall.service.ClientGoodsProductService;
import com.young.mall.service.ClientGoodsService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * @Description: 购物车业务
 * @Author: yqz
 * @CreateDate: 2020/11/28 16:46
 */
@Api(tags = "ClientCartController")
@RestController
@RequestMapping("/client/cart")
public class ClientCartController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientCartService clientCartService;

    @Autowired
    private ClientGoodsService clientGoodsService;

    @Autowired
    private ClientGoodsProductService clientGoodsProductService;


    @ApiOperation("购物车首页")
    @GetMapping("/index")
    public ResBean index() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        return clientCartService.index(userInfo.getYoungUser().getId());
    }

    @ApiOperation("用户购物车数量")
    @GetMapping("/goodscount")
    public ResBean goodsCount() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        List<YoungCart> cartList = clientCartService.queryByUid(userInfo.getYoungUser().getId());
        int goodsCount = 0;
        for (YoungCart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResBean.success(goodsCount);
    }

    /**
     * 如果已经存在购物车货品，则增加数量； 否则添加新的购物车货品项。
     *
     * @param cart 购物车商品信息， { goodsId: xxx, productId: xxx, number: xxx }
     * @return
     */
    @ApiOperation("加入商品到购物车")
    @PostMapping("/add")
    public ResBean addGoodsToCart(@RequestBody YoungCart cart) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户添加购物车失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer productId = cart.getProductId();
        int number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        //非空校验
        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResBean.validateFailed();
        }

        // 判断商品是否可以购买
        YoungGoods goods = clientGoodsService.findById(goodsId);
        if (BeanUtil.isEmpty(goods) || !goods.getIsOnSale()) {
            logger.error("添加商品到购物车失败：{}", ClientResponseCode.GOODS_UNSHELVE);
            return ResBean.failed(ClientResponseCode.GOODS_UNSHELVE);
        }

        //根据商品货品表的货品id，查询商品货品
        YoungGoodsProduct goodsProduct = clientGoodsProductService.findById(productId);
        // 取得规格的信息,判断规格库存
        if (BeanUtil.isEmpty(goodsProduct) || number > goodsProduct.getNumber()) {
            logger.error("{}->库存不足", cart.getGoodsName());
            return ResBean.failed("该商品目前库存不足");
        }
        // 判断购物车中是否存在此规格商品
        YoungCart existCart = clientCartService.queryExist(goodsId, productId, userInfo.getYoungUser().getId());
        if (BeanUtil.isEmpty(existCart)) {

            BeanUtil.copyProperties(goods, cart);
            cart.setGoodsName(goods.getName());
            cart.setPrice(goodsProduct.getPrice());
            cart.setSpecifications(goodsProduct.getSpecifications());
            cart.setUserId(userInfo.getYoungUser().getId());
            cart.setChecked(true);
            clientCartService.add(cart);
        } else {
            // 取得规格的信息,判断规格库存
            int num = existCart.getNumber() + number;
            //判断购物车中累计的数量是否大于总库存
            if (num > goodsProduct.getNumber()) {
                logger.error("加入商品到购物车失败:{}", ClientResponseCode.GOODS_NO_STOCK);
                return ResBean.failed(ClientResponseCode.GOODS_NO_STOCK);
            }
            existCart.setNumber(((short) number));
            if (clientCartService.updateById(existCart) == 0) {
                logger.error("加入商品到购物车失败:更新购物车信息失败!");
                return ResBean.failed(505, "更新数据失败");
            }
        }
        return this.goodsCount();
    }

    @ApiOperation("勾选购物车商品")
    @PostMapping("/checked")
    public ResBean checked(@Valid @RequestBody CartCheckDto cartCheckDto) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户添加购物车失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        boolean isChecked = cartCheckDto.getIsChecked() == 1;

        Integer count = clientCartService.updateCheck(userInfo.getYoungUser().getId(), cartCheckDto.getProductIds(), isChecked);
        if (count >= 1) {
            return this.index();
        }
        return ResBean.failed("勾选购物车商品");
    }


    @ApiOperation("修改购物车商品货品数量")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungCart cart) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户添加购物车失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer productId = cart.getProductId();
        Integer number = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        Integer id = cart.getId();
        if (!ObjectUtils.allNotNull(id, productId, number, goodsId)) {
            return ResBean.validateFailed();
        }

        // 判断是否存在该订单
        // 如果不存在，直接返回错误
        YoungCart existCart = clientCartService.findById(id);
        if (BeanUtil.isEmpty(existCart)) {
            return ResBean.failed("该商品不在购车内");
        }

        // 判断goodsId和productId是否与当前cart里的值一致
        if (!existCart.getGoodsId().equals(goodsId)) {
            logger.error("当前修改商品信息与购物车中不一致");
            return ResBean.failed("当前修改商品信息与购物车中不一致");
        }
        if (!existCart.getProductId().equals(productId)) {
            logger.error("当前修改商品信息与购物车中不一致");

            return ResBean.failed("当前修改商品信息与购物车中不一致");
        }
        // 判断商品是否可以购买
        YoungGoods goods = clientGoodsService.findById(goodsId);
        if (BeanUtil.isEmpty(goods) || !goods.getIsOnSale()) {
            logger.error("修改购物车商品失败：{}", ClientResponseCode.GOODS_UNSHELVE);
            return ResBean.failed(ClientResponseCode.GOODS_UNSHELVE);
        }
        // 取得规格的信息,判断规格库存
        YoungGoodsProduct product = clientGoodsProductService.findById(productId);
        if (BeanUtil.isEmpty(product) || product.getNumber() < number) {
            logger.error("{}->修改购物车失败：{}", userInfo.getYoungUser().getNickname(), ClientResponseCode.GOODS_NO_STOCK);
        }
        existCart.setNumber(number.shortValue());

        Integer count = clientCartService.updateById(existCart);
        if (count == 0) {
            logger.error("{}->修改购物车失败:更新购物车信息失败!", userInfo.getYoungUser().getNickname());
        }

        return ResBean.success("修改购物车成功");
    }

    /**
     * @param body 购物车商品信息， { productIds: xxx }
     * @return 删除结果
     */
    @ApiOperation("购物车商品删除")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody Map<String, List<Integer>> body) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户添加购物车失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }

        List<Integer> productIds = body.get("productIds");
        if (productIds == null || productIds.size() == 0) {
            return ResBean.validateFailed();
        }

        Integer count = clientCartService.delete(productIds, userInfo.getYoungUser().getId());
        logger.error(userInfo.getYoungUser().getUsername(), "{}:删除购物车数量：{}", count);
        return this.index();
    }

    /**
     * @param cartId         购物车商品ID： 如果购物车商品ID是空，则下单当前用户所有购物车商品； 如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId      收货地址ID： 如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId       优惠券ID： 如果优惠券ID是空，则自动选择合适的优惠券。
     * @param grouponRulesId
     * @return
     */
    @ApiOperation("购物车下单")
    @GetMapping("/checkout")
    public ResBean checkout(@RequestParam("cartId") Integer cartId, @RequestParam("addressId")Integer addressId,
                            @RequestParam("couponId") Integer couponId, @RequestParam("grouponRulesId") Integer grouponRulesId) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("用户添加购物车失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        //收货地址

        ResBean resBean = clientCartService.checkOut(userInfo.getYoungUser().getId(), cartId, addressId, couponId, grouponRulesId);
        return resBean;
    }

    /**
     * 立即购买
     * 和add方法的区别在于：
     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
     *
     * @return
     */
    @ApiOperation("立即购买")
    @PostMapping("/fastAdd")
    public ResBean fastAdd(@Valid @RequestBody FastAddVo fastAddVo) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("立即购买失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        return clientCartService.fastAdd(userInfo.getYoungUser().getId(), fastAddVo);
    }
}
