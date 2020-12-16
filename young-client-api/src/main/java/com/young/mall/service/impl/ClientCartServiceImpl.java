package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.dao.YoungCartMapper;
import com.young.db.entity.*;
import com.young.mall.common.ResBean;
import com.young.mall.domain.BrandCartGoods;
import com.young.mall.domain.CouponUserConstant;
import com.young.mall.domain.enums.WxResponseCode;
import com.young.mall.service.*;
import com.young.mall.system.SystemConfig;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description: 购物车业务
 * @Author: yqz
 * @CreateDate: 2020/11/28 16:55
 */
@Service
public class ClientCartServiceImpl implements ClientCartService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungCartMapper youngCartMapper;

    @Autowired
    private MallBrandService mallBrandService;

    @Autowired
    private ClientAddressService clientAddressService;

    @Autowired
    private MallGroupRuleService mallGroupRuleService;

    @Autowired
    private ClientCouponUserService clientCouponUserService;

    @Autowired
    private CouponVerifyService couponVerifyService;

    @Override
    public ResBean<Map<String, Object>> index(Integer uid) {

        List<YoungCart> cartList = queryByUid(uid);
        //商品数量
        Integer goodsCount = 0;
        //总价
        BigDecimal goodsAmount = new BigDecimal(0);
        //选中状态的商品数量
        Integer checkedGoodsCount = 0;
        //选中状态商品的总价
        BigDecimal checkedGoodsAmount = new BigDecimal(0);

        /*
         * public BigDecimal add(BigDecimal value);      //加法
         * public BigDecimal subtract(BigDecimal value); //减法
         * public BigDecimal multiply(BigDecimal value); //乘法
         * public BigDecimal divide(BigDecimal value);   //除法
         * */
        for (YoungCart cart : cartList) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            //判断商品是否是选中状态
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        Map<String, Object> cartTotal = new HashMap<>(6);
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>(8);
        result.put("cartTotal", cartTotal);

        // 如果需要拆订单，则需要按店铺显示购物车商品
        if (SystemConfig.isMultiOrderModel()) {
            result.put("isMultiOrderModel", 1);
            List<BrandCartGoods> brandCartGoodsList = new ArrayList<>();
            for (YoungCart cart : cartList) {
                //入驻品牌商编码
                Integer brandId = cart.getBrandId();
                boolean hasExist = false;
                for (int i = 0; i < brandCartGoodsList.size(); i++) {
                    if (brandCartGoodsList.get(i).getBrandId().intValue() == brandId.intValue()) {
                        brandCartGoodsList.get(i).getCartList().add(cart);
                        hasExist = true;
                        //结束当前循环
                        break;
                    }
                }
                // 还尚未加入，则需要查询品牌入驻商铺
                if (!hasExist) {
                    Optional<YoungBrand> optional = mallBrandService.findById(brandId);
                    if (!optional.isPresent()) {
                        return ResBean.failed("查询失败");
                    }
                    YoungBrand brand = optional.get();
                    BrandCartGoods brandCartGoods = BrandCartGoods.init(brand);
                    List<YoungCart> youngCartList = new ArrayList<>();
                    youngCartList.add(cart);
                    brandCartGoods.setCartList(youngCartList);
                    brandCartGoodsList.add(brandCartGoods);
                }
            }
            result.put("brandCartGoods", brandCartGoodsList);
        } else {
            result.put("isMultiOrderModel", 0);
            result.put("cartList", cartList);
        }

        return ResBean.success(result);
    }

    @Override
    public YoungCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        YoungCartExample example = new YoungCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId)
                .andDeletedEqualTo(false);
        return youngCartMapper.selectOneByExample(example);
    }

    @Override
    public Integer add(YoungCart cart) {
        cart.setAddTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        return youngCartMapper.insertSelective(cart);
    }

    @Override
    public Integer updateById(YoungCart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        return youngCartMapper.updateByPrimaryKeySelective(cart);
    }

    @Override
    public Integer updateCheck(Integer userId, List<Integer> idsList, boolean checked) {
        YoungCartExample example = new YoungCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList).andDeletedEqualTo(false);
        YoungCart cart = new YoungCart();
        cart.setChecked(checked);
        cart.setUpdateTime(LocalDateTime.now());
        return youngCartMapper.updateByExampleSelective(cart, example);
    }

    @Override
    public List<YoungCart> queryByUid(Integer uid) {

        YoungCartExample example = new YoungCartExample();
        example.createCriteria().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        List<YoungCart> cartList = youngCartMapper.selectByExample(example);
        return cartList;
    }

    @Override
    public List<YoungCart> queryByUidAndChecked(Integer userId) {
        YoungCartExample example = new YoungCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return youngCartMapper.selectByExample(example);
    }

    @Override
    public YoungCart findById(Integer id) {

        return youngCartMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer delete(List<Integer> productIdList, int userId) {
        YoungCartExample example = new YoungCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        return youngCartMapper.logicalDeleteByExample(example);
    }

    @Transactional
    @Override
    public ResBean checkOut(Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {

        // 收货地址
        YoungAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = clientAddressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收获地址
            // 返回一个空的地址id=0，这样前端则会提醒添加地址
            if (BeanUtil.isEmpty(checkedAddress)) {
                checkedAddress = new YoungAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }
        } else {
            checkedAddress = clientAddressService.findById(addressId);
            // 如果null, 则报错
            if (checkedAddress == null) {
                return ResBean.failed("参数值不对");
            }
        }

        // 团购优惠,如果是团购下单，只能单次购买一个商品，因为是从快速下单触发而来的。
        BigDecimal grouponPrice = new BigDecimal("0.00");
        YoungGrouponRules grouponRules = mallGroupRuleService.queryById(grouponRulesId);
        if (!BeanUtil.isEmpty(grouponRules)) {
            //优惠金额
            grouponPrice = grouponRules.getDiscount();
        }

        // 商品价格
        List<YoungCart> checkedGoodsList = null;
        // 如果未从购物车发起的下单，则获取用户选好的商品
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = this.queryByUidAndChecked(userId);
        } else {
            YoungCart cart = this.findById(cartId);
            if (BeanUtil.isEmpty(cart)) {
                return ResBean.failed("参数值不对");
            }
            checkedGoodsList = new ArrayList<>(4);
            checkedGoodsList.add(cart);
        }

        Map<String, Object> data = new HashMap<>();
        // 商品总价 （包含团购减免，即减免团购后的商品总价，多店铺需将所有商品相加）
        BigDecimal goodsTotalPrice = new BigDecimal("0.00");
        // 总配送费 （单店铺模式一个，多店铺模式多个配送费的总和）
        BigDecimal totalFreightPrice = new BigDecimal("0.00");

        // 如果需要拆订单，则按店铺进行拆分,如果不拆订单，则统一呈现
        // 需要拆订单，则需要按店铺显示购物车商品
        if (SystemConfig.isMultiOrderModel()) {
            // a.按入驻店铺归类checkout商品
            List<BrandCartGoods> brandCartgoodsList = new ArrayList<>();
            for (YoungCart cart : checkedGoodsList) {
                Integer brandId = cart.getBrandId();
                boolean hasExist = false;
                for (int i = 0; i < brandCartgoodsList.size(); i++) {
                    if (brandCartgoodsList.get(i).getBrandId().intValue() == brandId.intValue()) {
                        brandCartgoodsList.get(i).getCartList().add(cart);
                        hasExist = true;
                        //结束当前循环
                        break;
                    }
                }
                // 还尚未加入，则需要查询品牌入驻商铺
                if (!hasExist) {
                    YoungBrand brand = mallBrandService.findById(brandId).get();
                    BrandCartGoods bandCartGoods = BrandCartGoods.init(brand);
                    List<YoungCart> cartList = new ArrayList<>();
                    cartList.add(cart);
                    bandCartGoods.setCartList(cartList);
                    brandCartgoodsList.add(bandCartGoods);
                }
            }
            // b.核算每个店铺的各项价格指标
            List<BrandCartGoods> checkBrandGoodsList = new ArrayList<>();
            for (BrandCartGoods bcg : brandCartgoodsList) {
                List<YoungCart> bandCarts = bcg.getCartList();
                BigDecimal bandGoodsTotalPrice = new BigDecimal("0.00");
                BigDecimal bandFreightPrice = new BigDecimal("0.00");

                // 循环店铺各自的购物商品
                for (YoungCart cart : bandCarts) {
                    // 只有当团购规格商品ID符合才进行团购优惠
                    if (grouponRules != null && grouponRules.getGoodsSn().equals(cart.getGoodsSn())) {
                        bandGoodsTotalPrice = bandGoodsTotalPrice
                                .add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
                    } else {
                        bandGoodsTotalPrice = bandGoodsTotalPrice
                                .add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
                    }
                }

                // 每个店铺都单独计算运费，满xxx则免运费，否则按配置的邮寄费x元计算；
                if (bandGoodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                    bandFreightPrice = SystemConfig.getFreight();
                }

                goodsTotalPrice = goodsTotalPrice.add(bandGoodsTotalPrice);
                totalFreightPrice = totalFreightPrice.add(bandFreightPrice);

                bcg.setBandGoodsTotalPrice(bandGoodsTotalPrice);
                bcg.setBandFreightPrice(bandFreightPrice);

                checkBrandGoodsList.add(bcg);
            }
            data.put("isMultiOrderModel", 1);
            data.put("goodsTotalPrice", goodsTotalPrice);
            data.put("freightPrice", totalFreightPrice);
            data.put("brandCartgoods", checkBrandGoodsList);
            // 不拆订单，则统一呈现
        } else {
            for (YoungCart cart : checkedGoodsList) {
                // 只有当团购规格商品ID符合才进行团购优惠
                if (grouponRules != null && grouponRules.getGoodsSn().equals(cart.getGoodsSn())) {
                    goodsTotalPrice = goodsTotalPrice
                            .add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
                } else {
                    goodsTotalPrice = goodsTotalPrice
                            .add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
                }
            }

            // 根据订单商品总价计算运费，满66则免运费，否则6元；
            if (goodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                totalFreightPrice = SystemConfig.getFreight();
            }

            data.put("isMultiOrderModel", 0);
            data.put("goodsTotalPrice", goodsTotalPrice);
            data.put("freightPrice", totalFreightPrice);
            data.put("checkedGoodsList", checkedGoodsList);
        }
        // 计算优惠券可用情况
        BigDecimal tmpCouponPrice = new BigDecimal("0.00");
        Integer tmpCouponId = 0;
        int tmpCouponLength = 0;
        List<YoungCouponUser> couponUserList = clientCouponUserService.queryList(userId, null, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
        for (YoungCouponUser couponUser : couponUserList) {

            YoungCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), goodsTotalPrice);
            if (coupon == null) {
                continue;
            }
            tmpCouponLength++;
            if (tmpCouponPrice.compareTo(coupon.getDiscount()) == -1) {
                tmpCouponPrice = coupon.getDiscount();
                tmpCouponId = coupon.getId();
            }
        }
        // 获取优惠券减免金额，优惠券可用数量
        int availableCouponLength = tmpCouponLength;
        BigDecimal couponPrice = new BigDecimal(0);
        /**
         * 这里存在三种情况:
         * 1. 用户不想使用优惠券，则不处理
         * 2. 用户想自动使用优惠券，则选择合适优惠券
         * 3. 用户已选择优惠券，则测试优惠券是否合适
         */
        // 1. 用户不想使用优惠券，则不处理
        if (couponId == null || couponId.equals(-1)) {
            couponId = -1;
            // 2. 用户想自动使用优惠券，则选择合适优惠券
        } else if (couponId.equals(0)) {
            couponPrice = tmpCouponPrice;
            couponId = tmpCouponId;
        } else { // 3. 用户已选择优惠券，则测试优惠券是否合适 ，购买商品总价（有团购商品需按团购商品后的价格计算）要超出券的最低消费金额
            YoungCoupon coupon = couponVerifyService.checkCoupon(userId, couponId, goodsTotalPrice);
            // 用户选择的优惠券有问题
            if (coupon == null) {
                logger.error("用户购物车下单失败:{}", WxResponseCode.INVALID_COUPON.getMsg());
                return ResBean.failed(WxResponseCode.INVALID_COUPON);
            }
            couponPrice = coupon.getDiscount();
        }
        // 用户积分减免
        BigDecimal integralPrice = new BigDecimal("0.00");

        BigDecimal orderTotalPrice = goodsTotalPrice.add(totalFreightPrice).subtract(couponPrice);
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        // 返回界面的通用数据
        data.put("addressId", addressId);
        data.put("checkedAddress", checkedAddress);
        data.put("couponId", couponId);
        data.put("availableCouponLength", availableCouponLength);
        data.put("grouponRulesId", grouponRulesId);

        // 团购优惠的商品价格（团购商品需减免的优惠金额）
        data.put("grouponPrice", grouponPrice);
        // 单店铺，多店铺 一个总订单都只能用一张券
        data.put("couponPrice", couponPrice);

        // 订单总价：goodsTotalPrice + totalFreightPrice - couponPrice
        data.put("orderTotalPrice", orderTotalPrice);
        // 订单实际付款金额：orderTotalPrice - integralPrice
        data.put("actualPrice", actualPrice);

        return ResBean.success(data);
    }
}
