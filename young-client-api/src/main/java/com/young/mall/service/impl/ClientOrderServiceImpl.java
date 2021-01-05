package com.young.mall.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.*;
import com.young.mall.common.ResBean;
import com.young.mall.constant.CommonConstants;
import com.young.mall.domain.BrandCartGoods;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.CouponUserConstant;
import com.young.mall.domain.enums.ClientResponseCode;
import com.young.mall.domain.vo.OrderCommentVo;
import com.young.mall.domain.vo.OrderVo;
import com.young.mall.domain.vo.SubmitOrderVo;
import com.young.mall.dto.MailDto;
import com.young.mall.exception.Asserts;
import com.young.mall.express.ExpressService;
import com.young.mall.express.entity.ExpressInfo;
import com.young.mall.express.entity.Traces;
import com.young.mall.notify.NotifyService;
import com.young.mall.notify.NotifyType;
import com.young.mall.service.*;
import com.young.mall.system.SystemConfig;
import com.young.mall.utils.IpUtil;
import com.young.mall.utils.OrderHandleOption;
import com.young.mall.utils.OrderUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description: 订单业务
 * @Author: yqz
 * @CreateDate: 2020/12/10 17:29
 */
@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private YoungOrderMapper youngOrderMapper;

    @Resource
    private ClientGrouponService youngGrouponService;

    @Resource
    private ClientOrderGoodsService clientOrderGoodsService;

    @Resource
    private ExpressService expressService;

    @Resource
    private ClientUserService clientUserService;

    @Resource
    private ClientCommentService clientCommentService;

    @Resource
    private ClientGrouponRulesService clientGrouponRulesService;

    @Resource
    private ClientAddressService clientAddressService;

    @Resource
    private ClientCartService clientCartService;

    @Resource
    private CouponVerifyService couponVerifyService;

    @Resource
    private ClientRegionService clientRegionService;

    @Resource
    private ClientAccountService clientAccountService;

    @Resource
    private ClientGoodsProductService clientGoodsProductService;

    @Resource
    private ClientCouponUserService clientCouponUserService;

    @Resource
    private ClientGrouponService clientGrouponService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private ClientUserFormIdService clientUserFormIdService;

    @Resource
    private NotifyService notifyService;

    @Override
    public Map<String, Object> orderInfo(Integer userId) {

        YoungOrderExample example = new YoungOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);

        List<YoungOrder> orders = youngOrderMapper.selectByExampleSelective(example, YoungOrder.Column.orderStatus, YoungOrder.Column.comments);
        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;

        for (YoungOrder order : orders) {
            if (OrderUtil.isCreateStatus(order)) {
                unpaid++;
            } else if (OrderUtil.isPayStatus(order)) {
                unship++;
            } else if (OrderUtil.isShipStatus(order)) {
                unrecv++;
            } else if (OrderUtil.isConfirmStatus(order) || OrderUtil.isAutoConfirmStatus(order)) {
                uncomment += order.getComments();
            } else {
                // do nothing
            }
        }
        Map<String, Object> orderInfo = new HashMap<>(6);
        orderInfo.put("unpaid", unpaid);
        orderInfo.put("unship", unship);
        orderInfo.put("unrecv", unrecv);
        orderInfo.put("uncomment", uncomment);
        return orderInfo;
    }

    @Override
    public Map<String, Object> list(Integer userId, Integer showType, Integer page, Integer size) {

        List<Short> orderStatus = OrderUtil.orderStatus(showType);

        List<YoungOrder> orderList = this.queryByOrderStatus(userId, orderStatus, page, size);

        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());

        for (YoungOrder order : orderList) {
            Map<String, Object> orderVo = new HashMap<>(20);
            orderVo.put("id", order.getId());
            orderVo.put("orderSn", order.getOrderSn());
            orderVo.put("addTime", order.getAddTime());
            orderVo.put("consignee", order.getConsignee());
            orderVo.put("mobile", order.getMobile());
            orderVo.put("address", order.getAddress());
            orderVo.put("goodsPrice", order.getGoodsPrice());
            orderVo.put("freightPrice", order.getFreightPrice());
            orderVo.put("actualPrice", order.getActualPrice());
            orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
            orderVo.put("handleOption", OrderUtil.build(order));
            orderVo.put("expCode", order.getShipChannel());
            orderVo.put("expNo", order.getShipSn());
            YoungGroupon groupon = youngGrouponService.queryByOrderId(order.getId());

            if (groupon != null) {
                orderVo.put("isGroupin", true);
            } else {
                orderVo.put("isGroupin", false);
            }

            List<YoungOrderGoods> orderGoodsList = clientOrderGoodsService.queryByOid(order.getId());
            orderVo.put("goodsList", orderGoodsList);
            orderVoList.add(orderVo);

        }
        Map<String, Object> result = new HashMap<>(4);

        PageInfo<YoungOrder> pageInfo = PageInfo.of(orderList);
        result.put("count", pageInfo.getTotal());
        result.put("data", orderVoList);
        result.put("totalPages", pageInfo.getPages());
        return result;
    }

    @Override
    public List<YoungOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer size) {
        YoungOrderExample example = new YoungOrderExample();
        example.setOrderByClause(YoungOrder.Column.addTime.desc());
        YoungOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        PageHelper.startPage(page, size);
        return youngOrderMapper.selectByExample(example);
    }

    @Override
    public YoungOrder findById(Integer orderId) {
        return youngOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public Map<String, Object> detail(Integer userId, Integer orderId) {

        //订单信息
        YoungOrder order = this.findById(orderId);
        if (BeanUtil.isEmpty(order)) {
            logger.error("{}:获取订单详情失败：{}", userId, ClientResponseCode.ORDER_UNKNOWN.getMsg());
            Asserts.fail(ClientResponseCode.ORDER_UNKNOWN);
        }
        if (!order.getUserId().equals(userId)) {
            logger.error("{}:获取订单详情失败：{}", userId, ClientResponseCode.ORDER_INVALID.getMsg());
            Asserts.fail(ClientResponseCode.ORDER_INVALID);
        }

        OrderVo orderVo = OrderVo.builder()
                .id(order.getId())
                .orderSn(order.getOrderSn())
                .addTime(order.getAddTime())
                .consignee(order.getConsignee())
                .mobile(order.getMobile())
                .address(order.getAddress())
                .goodsPrice(order.getGoodsPrice())
                .freightPrice(order.getFreightPrice())
                .discountPrice(order.getIntegralPrice().add(order.getGrouponPrice()).add(order.getCouponPrice()))
                .actualPrice(order.getActualPrice())
                .orderStatusText(OrderUtil.orderStatusText(order))
                .handleOption(OrderUtil.build(order))
                .expCode(order.getShipChannel())
                .expNo(order.getShipSn()).build();

        List<YoungOrderGoods> orderGoodsList = clientOrderGoodsService.queryByOid(orderId);
        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderVo);
        result.put("orderGoods", orderGoodsList);

        // 订单状态为已发货且物流信息不为空
        // "YTO", "800669400640887922"

        if (OrderUtil.STATUS_SHIP.equals(order.getOrderStatus())) {
            ExpressInfo expressInfo = expressService.getExpressInfo(order.getShipChannel(), order.getShipSn());
            result.put("expressInfo", expressInfo);
        }
        return result;
    }

    @Override
    public Map<String, Object> expressTrace(Integer userId, Integer orderId) {

        // 订单信息
        YoungOrder order = this.findById(orderId);
        if (BeanUtil.isEmpty(order)) {
            logger.error("{}:获取订单详情失败：{}", userId, ClientResponseCode.ORDER_UNKNOWN.getMsg());
            Asserts.fail(ClientResponseCode.ORDER_UNKNOWN);
        }
        if (!order.getUserId().equals(userId)) {
            logger.error("{}:获取订单详情失败：{}", userId, ClientResponseCode.ORDER_INVALID.getMsg());
            Asserts.fail(ClientResponseCode.ORDER_INVALID);
        }
        Map<String, Object> result = new HashMap<>();
        DateTimeFormatter dateSdf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter timeSdf = DateTimeFormatter.ofPattern("HH:mm");

        result.put("shipDate", dateSdf.format(order.getShipTime()));
        result.put("shipTime", timeSdf.format(order.getShipTime()));
        result.put("shipperCode", order.getShipSn());
        result.put("address", order.getAddress());

        if (OrderUtil.STATUS_SHIP.equals(order.getOrderStatus())) {
            ExpressInfo ei = expressService.getExpressInfo(order.getShipChannel(), order.getShipSn());
            if (!BeanUtil.isEmpty(ei)) {
                result.put("state", ei.getState());
                result.put("shipperName", ei.getShipperName());
                List<Traces> eiTrace = ei.getTraces();
                List<Map<String, Object>> traces = new ArrayList<>();
                for (Traces trace : eiTrace) {
                    Map<String, Object> traceMap = new HashMap<>();
                    traceMap.put("date", trace.getAcceptTime().substring(0, 10));
                    traceMap.put("time", trace.getAcceptTime().substring(11, 16));
                    traceMap.put("acceptTime", trace.getAcceptTime());
                    traceMap.put("acceptStation", trace.getAcceptStation());
                    traces.add(traceMap);
                }
                result.put("traces", traces);
            }
        }
        return result;
    }

    @Override
    public YoungOrderGoods getGoodsByIds(Integer userId, Integer orderId, Integer goodsId) {

        List<YoungOrderGoods> orderGoodsList = clientOrderGoodsService.findByOidAndGid(orderId, goodsId);

        int size = orderGoodsList.size();
        //断言处理，如果第一个条件为true，则抛出 “存在多个符合条件的订单商品” 异常
        Asserts.state(size > 1, "存在多个符合条件的订单商品");

        Asserts.state(size == 0, "未查询到订单");

        return orderGoodsList.get(0);
    }

    @Transactional
    @Override
    public ResBean comment(OrderCommentVo commentVo) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.error("查询待评价订单商品信息失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();

        //判断是否有此订单商品
        YoungOrderGoods orderGoods = clientOrderGoodsService.findById(commentVo.getOrderGoodsId());
        if (BeanUtil.isEmpty(orderGoods)) {
            return ResBean.failed("未查询到订单商品");
        }
        //判断是否存在此订单
        YoungOrder order = this.findById(orderGoods.getOrderId());
        if (BeanUtil.isEmpty(order)) {
            return ResBean.failed("未查询到此订单");
        }

        //当401用户确认收货以后，可以评价
        //当402系统自动确认收货以后，可以评价
        if (!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)) {
            logger.error("评价订单商品失败:{}", ClientResponseCode.ORDER_NOT_COMMENT.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_NOT_COMMENT);
        }

        //判断当前用户是否可以评论
        if (!userId.equals(order.getUserId())) {
            logger.error("评价订单商品失败：{}", ClientResponseCode.ORDER_INVALID.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_INVALID);
        }

        //订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID
        Integer commentId = orderGoods.getComment();
        if (commentId == -1) {
            logger.error("评价订单商品失败：{}", ClientResponseCode.ORDER_COMMENT_EXPIRED.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_COMMENT_EXPIRED);
        }

        if (commentId != 0) {
            logger.error("评价订单商品失败：{}", ClientResponseCode.ORDER_COMMENTED.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_COMMENTED);
        }

        Integer star = commentVo.getStar();
        if (star > 5 || star < 0) {
            return ResBean.failed("评分只能为  1-5");
        }

        //判断是否存在图片，不存在的话new一个空集合
        Boolean hasPicture = commentVo.getHasPicture();
        List<String> picUrls = commentVo.getPicUrls();
        if (!hasPicture) {
            picUrls = new ArrayList<>(0);
        }

        // 1、创建评价
        YoungComment comment = new YoungComment();
        comment.setUserId(userId);
        comment.setType((byte) 0);
        comment.setValueId(orderGoods.getGoodsId());
        comment.setStar(star.shortValue());
        comment.setContent(commentVo.getContent());
        comment.setHasPicture(hasPicture);
        comment.setPicUrls(picUrls.toArray(new String[]{}));

        //存入数据库
        Integer count = clientCommentService.save(comment);
        //2、更新订单商品评论表
        orderGoods.setComment(comment.getId());
        Integer upCount = clientOrderGoodsService.updateById(orderGoods);

        // 3. 更新订单中未评价的订单商品可评价数量
        Short commentCount = order.getComments();
        if (commentCount > 0) {
            commentCount--;
        }
        order.setComments(commentCount);
        Integer update = updateWithOptimisticLocker(order);

        return ResBean.success("评论成功");
    }

    @Override
    public Integer updateWithOptimisticLocker(YoungOrder order) {

        order.setUpdateTime(LocalDateTime.now());

        return youngOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Transactional
    @Override
    public ResBean submit(Integer userId, SubmitOrderVo submitOrder) {

        Integer cartId = submitOrder.getCartId();
        Integer grouponLinkId = submitOrder.getGrouponLinkId();
        Integer grouponRulesId = submitOrder.getGrouponRulesId();
        Integer couponId = submitOrder.getCouponId();
        Integer addressId = submitOrder.getAddressId();
        //查询团购规则
        YoungGrouponRules rules = clientGrouponRulesService.queryById(grouponRulesId);
        //如果是团购项目，验证码活动是否有效
        //grouponRulesId  为整型，默认为0
        if (grouponRulesId > 0) {

            //找不到记录
            if (BeanUtil.isEmpty(rules)) {
                logger.error("用户：{}，未查询到该团购信息", userId);
                return ResBean.failed("未查询到该团购信息");
            }
            //如果团购活动已经过期
            if (clientGrouponRulesService.isExpired(rules)) {
                logger.error("提交订单详情失败:{}", ClientResponseCode.GROUPON_EXPIRED.getMsg());
                return ResBean.failed(ClientResponseCode.GROUPON_EXPIRED);
            }
        }
        // 收货地址
        YoungAddress checkedAddress = clientAddressService.findById(addressId);
        if (BeanUtil.isEmpty(checkedAddress)) {
            return ResBean.failed("目前无收货地址");
        }

        // 团购优惠,如果是团购下单，只能单次购买一个商品
        BigDecimal grouponPrice = new BigDecimal("0.00");
        if (!BeanUtil.isEmpty(rules)) {
            grouponPrice = rules.getDiscount();
        }

        //计算运费和商品总价
        Map<String, Object> goodsPrice = this.getAllGoodsPrice(userId, cartId, rules, grouponPrice);
        List<YoungCart> checkedGoodsList = (List<YoungCart>) goodsPrice.get("checkedGoodsList");

        //把订单信息落库  young_order表
        YoungOrder order = this.addOrderData(userId, submitOrder, checkedAddress, goodsPrice, grouponPrice, rules);
        Integer orderId = order.getId();

        //添加订单商品表项
        this.addOrderGoods(order, checkedGoodsList);

        // 删除购物车里面的商品信息
//        Integer count = clientCartService.clearGoods(userId);

        Integer count = clientCartService.clearGoodsByCartId(cartId);

        // 商品货品数量减少
        for (YoungCart checkGoods : checkedGoodsList) {

            Integer productId = checkGoods.getProductId();

            YoungGoodsProduct product = clientGoodsProductService.findById(productId);

            Integer remainNumber = product.getNumber() - checkGoods.getNumber();
            if (remainNumber < 0) {
                Asserts.fail("下单的商品货品数量大于库存量");
            }
            if (clientGoodsProductService.reduceStock(productId, checkGoods.getGoodsId(), checkGoods.getNumber()) == 0) {
                Asserts.fail("商品货品库存减少失败");
            }
        }
        // 如果使用了优惠券，设置优惠券使用状态
        if (couponId != 0 && couponId != -1) {
            YoungCouponUser couponUser = clientCouponUserService.queryOne(userId, couponId);
            couponUser.setStatus(CouponUserConstant.STATUS_USED);
            couponUser.setUsedTime(LocalDateTime.now());
            couponUser.setOrderSn(order.getOrderSn());
            clientCouponUserService.update(couponUser);
        }

        // 如果是团购项目，添加团购信息
        if (grouponRulesId != null && grouponRulesId > 0) {
            YoungGroupon groupon = new YoungGroupon();
            groupon.setOrderId(orderId);
            groupon.setPayed(false);
            groupon.setUserId(userId);
            groupon.setRulesId(grouponRulesId);

            // 参与者
            if (grouponLinkId != null && grouponLinkId > 0) {
                // 参与的团购记录
                YoungGroupon baseGroupon = clientGrouponService.queryById(grouponLinkId);
                groupon.setCreatorUserId(baseGroupon.getCreatorUserId());
                groupon.setGrouponId(grouponLinkId);
                groupon.setShareUrl(baseGroupon.getShareUrl());
            } else {
                groupon.setCreatorUserId(userId);
                groupon.setGrouponId(0);
            }
            clientGrouponService.createGroupon(groupon);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        return ResBean.success(data);
    }

    @Override
    public Integer add(YoungOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return youngOrderMapper.insertSelective(order);
    }

    // TODO 这里应该产生一个唯一的订单，但是实际上这里仍然存在两个订单相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = getRandomNum(6);
        }
        return orderSn;
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int countByOrderSn(Integer userId, String orderSn) {
        YoungOrderExample example = new YoungOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) youngOrderMapper.countByExample(example);
    }

    private String detailedAddress(YoungAddress address) {
        Integer provinceId = address.getProvinceId();
        Integer cityId = address.getCityId();
        Integer areaId = address.getAreaId();
        String provinceName = clientRegionService.findById(provinceId).getName();
        String cityName = clientRegionService.findById(cityId).getName();
        String areaName = clientRegionService.findById(areaId).getName();
        String fullRegion = provinceName + " " + cityName + " " + areaName;
        return fullRegion + " " + address.getAddress();
    }

    //计算运费和商品总价
    private Map<String, Object> getAllGoodsPrice(Integer userId, Integer cartId, YoungGrouponRules rules, BigDecimal grouponPrice) {

        // 商品价格
        List<YoungCart> checkedGoodsList = null;
        // 如果未从购物车发起的下单，则获取用户选好的商品
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = clientCartService.queryByUidAndChecked(userId);
        } else {
            YoungCart cart = clientCartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(2);
            checkedGoodsList.add(cart);
        }

        // 商品总价 （包含团购减免，即减免团购后的商品总价，多店铺需将所有商品相加）
        BigDecimal goodsTotalPrice = new BigDecimal("0.00");
        // 总配送费 （单店铺模式一个，多店铺模式多个配送费的总和）
        BigDecimal totalFreightPrice = new BigDecimal("0.00");

        // 如果需要拆订单，则按店铺进行拆分,如果不拆订单，则统一呈现
        // 需要拆订单，则需要按店铺显示购物车商品
        if (SystemConfig.isMultiOrderModel()) {
            // a.按入驻店铺归类checkout商品
            List<BrandCartGoods> brandCartGoodsList = new ArrayList<>();
            for (YoungCart cart : checkedGoodsList) {
                Integer brandId = cart.getBrandId();
                boolean hasExist = false;
                for (int i = 0; i < brandCartGoodsList.size(); i++) {
                    //判断结账的商品是否属于同一个店铺
                    if (brandCartGoodsList.get(i).getBrandId().intValue() == brandId.intValue()) {
                        brandCartGoodsList.get(i).getCartList().add(cart);
                        hasExist = true;
                        //结束当前循环
                        break;
                    }
                }
                // 还尚未加入，则需要查询品牌入驻商铺
                if (!hasExist) {
                    BrandCartGoods bandCartGoods = new BrandCartGoods();
                    bandCartGoods.setBrandId(brandId);
                    List<YoungCart> cartList = new ArrayList<>();
                    cartList.add(cart);
                    bandCartGoods.setCartList(cartList);
                    brandCartGoodsList.add(bandCartGoods);
                }
            }
            // b.核算每个店铺的各项价格指标
            for (BrandCartGoods bcg : brandCartGoodsList) {
                List<YoungCart> bandCarts = bcg.getCartList();
                BigDecimal bandGoodsTotalPrice = new BigDecimal("0.00");
                BigDecimal bandFreightPrice = new BigDecimal("0.00");

                // 循环店铺各自的购物商品
                for (YoungCart cart : bandCarts) {
                    // 只有当团购规格商品ID符合才进行团购优惠
                    if (rules != null && rules.getGoodsSn().equals(cart.getGoodsSn())) {
                        bandGoodsTotalPrice = bandGoodsTotalPrice
                                .add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
                    } else {
                        bandGoodsTotalPrice = bandGoodsTotalPrice
                                .add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
                    }
                }

                // 每个店铺都单独计算运费，满66则免运费，否则6元；
                if (bandGoodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                    bandFreightPrice = SystemConfig.getFreight();
                }

                goodsTotalPrice = goodsTotalPrice.add(bandGoodsTotalPrice);
                totalFreightPrice = totalFreightPrice.add(bandFreightPrice);
            }

            // 不拆订单，则统一呈现
        } else {
            for (YoungCart cart : checkedGoodsList) {
                // 只有当团购规格商品ID符合才进行团购优惠
                if (rules != null && rules.getGoodsSn().equals(cart.getGoodsSn())) {
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
        }

        Map<String, Object> result = new HashMap<>();

        result.put("goodsTotalPrice", goodsTotalPrice);
        result.put("totalFreightPrice", totalFreightPrice);
        result.put("checkedGoodsList", checkedGoodsList);
        return result;
    }


    //把订单信息落库
    private YoungOrder addOrderData(Integer userId, SubmitOrderVo orderVo, YoungAddress checkedAddress,
                                    Map<String, Object> goodsPrice, BigDecimal grouponPrice, YoungGrouponRules rules) {
        BigDecimal goodsTotalPrice = (BigDecimal) goodsPrice.get("goodsTotalPrice");
        BigDecimal totalFreightPrice = (BigDecimal) goodsPrice.get("totalFreightPrice");
        // 获取可用的优惠券信息 使用优惠券减免的金额
        BigDecimal couponPrice = new BigDecimal("0.00");
        // 如果couponId=0则没有优惠券，couponId=-1则不使用优惠券
        if (orderVo.getCouponId() != 0 && orderVo.getCouponId() != -1) {
            YoungCoupon coupon = couponVerifyService.checkCoupon(userId, orderVo.getCouponId(), goodsTotalPrice);
            if (BeanUtil.isEmpty(coupon)) {
                logger.error("用户id：{}，无该用户券：{}", userId, orderVo);
                Asserts.fail("无该优惠券");
            }
            couponPrice = coupon.getDiscount();
        }
        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal("0.00");
        // 订单费用
        BigDecimal orderTotalPrice = goodsTotalPrice.add(totalFreightPrice).subtract(couponPrice);
        // 最终支付费用
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);
        // 订单
        YoungOrder order = new YoungOrder();
        order.setUserId(userId);
        order.setOrderSn(generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee(checkedAddress.getName());
        order.setMobile(checkedAddress.getMobile());
        order.setMessage(orderVo.getMessage());
        String detailedAddress = detailedAddress(checkedAddress);
        order.setAddress(detailedAddress);
        order.setGoodsPrice(goodsTotalPrice);
        order.setFreightPrice(totalFreightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);

        // 有团购活动
        if (!BeanUtil.isEmpty(rules)) {
            // 团购价格
            order.setGrouponPrice(grouponPrice);
        } else {
            // 团购价格
            order.setGrouponPrice(new BigDecimal("0.00"));
        }

        // 新增代理的结算金额计算
        YoungUser user = clientUserService.findById(userId);

        Integer shareUserId = 1;
        if (!BeanUtil.isEmpty(user) && user.getShareUserId() != null) {
            shareUserId = user.getShareUserId();
        }
        // 默认百分之3
        Integer settlementRate = 3;

        YoungUserAccount userAccount = clientAccountService.findShareUserAccountByUserId(shareUserId);

        if (userAccount != null && userAccount.getSettlementRate() > 0 && userAccount.getSettlementRate() < 15) {
            settlementRate = userAccount.getSettlementRate();
        }
        BigDecimal rate = new BigDecimal(settlementRate * 0.01);
        BigDecimal settlementMoney = (actualPrice.subtract(totalFreightPrice)).multiply(rate);
        order.setSettlementMoney(settlementMoney.setScale(2, BigDecimal.ROUND_DOWN));

        // 添加订单表项
        this.add(order);
        return order;
    }

    //添加OrderGoods表
    private void addOrderGoods(YoungOrder order, List<YoungCart> checkedGoodsList) {
        // 添加订单商品表项
        for (YoungCart cartGoods : checkedGoodsList) {
            // 订单商品
            YoungOrderGoods orderGoods = new YoungOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(cartGoods.getGoodsId());
            orderGoods.setGoodsSn(cartGoods.getGoodsSn());
            orderGoods.setProductId(cartGoods.getProductId());
            orderGoods.setGoodsName(cartGoods.getGoodsName());
            orderGoods.setPicUrl(cartGoods.getPicUrl());
            orderGoods.setPrice(cartGoods.getPrice());
            orderGoods.setNumber(cartGoods.getNumber());
            orderGoods.setSpecifications(cartGoods.getSpecifications());
            orderGoods.setAddTime(LocalDateTime.now());
            // 订单商品需加上入驻店铺标志
            orderGoods.setBrandId(cartGoods.getBrandId());

            clientOrderGoodsService.add(orderGoods);
        }

    }

    @Transactional
    @Override
    public ResBean prepay(Integer userId, Integer orderId, HttpServletRequest request) {

        YoungOrder order = this.findById(orderId);
        if (BeanUtil.isEmpty(order) || !userId.equals(order.getUserId())) {
            logger.error("用户：{}，无此订单:{}", userId, orderId);
            return ResBean.failed("无此订单");
        }
        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);

        if (!handleOption.isPay()) {
            logger.error("订单预付款失败：{}", ClientResponseCode.ORDER_REPAY_OPERATION.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_REPAY_OPERATION);
        }

        YoungUser user = clientUserService.findById(userId);
        String openid = user.getWeixinOpenid();
        if (StrUtil.isEmpty(openid)) {
            logger.error("订单预付款失败:{}", ClientResponseCode.AUTH_OPENID_UNACCESS.getMsg());
            return ResBean.failed(ClientResponseCode.AUTH_OPENID_UNACCESS);
        }

        //TODO 目前没有微信商户信息，暂时没有自测
        //微信支付
        WxPayMpOrderResult result = WxPayMpOrderResult.builder()
                .appId("wx2421b1c4370ec43b")
                .timeStamp(String.valueOf(System.currentTimeMillis() / 1000L))
                .nonceStr("1add1a30ac87aa2db72f57a2375d8fec")
                .packageValue("Sign=WXPay")
                .signType("MD5")
                .paySign("0CB01533B8C1EF103065174F50BCA001")
                .build();
 /*       try {
            result = this.wxPay(order, openid, request);

        } catch (Exception e) {
            logger.error("用户id：{}，付款订单的预支付会话标识失败：{},异常信息：{}", userId, ClientResponseCode.ORDER_PAY_FAIL.getMsg(), e.getMessage());
            return ResBean.failed(ClientResponseCode.ORDER_PAY_FAIL);
        }
*/
        // 缓存prepayID用于后续模版通知
        String prepayId = result.getPackageValue();
        prepayId = prepayId.replace("prepay_id=", "");
        YoungUserFormid userFormId = new YoungUserFormid();
        userFormId.setOpenid(openid);
        userFormId.setFormid(prepayId);
        userFormId.setIsprepay(true);
        userFormId.setUseamount(3);
        userFormId.setExpireTime(LocalDateTime.now().plusDays(7));
        clientUserFormIdService.addUserFormId(userFormId);
        if (this.updateWithOptimisticLocker(order) == 0) {
            logger.error("付款订单的预支付失败：{}", "更新订单信息失败");
            return ResBean.failed("更新订单信息失败");
        }

        return ResBean.success(result);
    }

    //调用微信支付
    private WxPayMpOrderResult wxPay(YoungOrder order, String openid, HttpServletRequest request) throws WxPayException {
        //预付款API文档
        //https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
        //商户订单类
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        //商户订单号
        orderRequest.setOutTradeNo(order.getOrderSn());
        //trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
        orderRequest.setOpenid(openid);
        //商品描述
        orderRequest.setBody(CommonConstants.DEFAULT_ORDER_FIX + order.getOrderSn());
        // 元转成分
        BigDecimal actualPrice = order.getActualPrice();
        int fee = actualPrice.multiply(new BigDecimal(100)).intValue();
        //订单总金额，单位为分
        orderRequest.setTotalFee(fee);
        //支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
        orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));

        WxPayMpOrderResult result = wxPayService.createOrder(orderRequest);
        return result;
    }


    /**
     * 取消订单
     * <p>
     * 1. 检测当前订单是否能够取消；
     * 2. 设置订单取消状态；
     * 3. 商品货品库存恢复；
     * 4. TODO 优惠券；
     * 5. TODO 团购活动。
     *
     * @param userId  用户id
     * @param orderId 订单id
     * @return
     */
    @Transactional
    @Override
    public ResBean cancel(Integer userId, Integer orderId) {

        YoungOrder order = this.findById(orderId);

        if (BeanUtil.isEmpty(order) || !userId.equals(order.getUserId())) {
            return ResBean.failed("目前用户与该订单不匹配");
        }

        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isCancel()) {
            logger.error("取消订单失败：{}", ClientResponseCode.ORDER_INVALID_OPERATION.getMsg());
            return ResBean.failed(ClientResponseCode.ORDER_INVALID_OPERATION);
        }
        //设置订单为已取消状态
        order.setOrderStatus(OrderUtil.STATUS_CANCEL);
        order.setEndTime(LocalDateTime.now());
        if (this.updateWithOptimisticLocker(order) != 1) {
            return ResBean.failed("更新数据失败");
        }

        // 商品货品数量增加
        List<YoungOrderGoods> orderGoodsList = clientOrderGoodsService.queryByOid(orderId);
        for (YoungOrderGoods orderGoods : orderGoodsList) {

            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();

            if (clientGoodsProductService.addStock(productId, number) != 1) {
                return ResBean.failed("增加库存失败");
            }
        }
        return ResBean.success("取消订单成功");
    }

    @Override
    public ResBean refund(YoungUser user, Integer orderId) {

        YoungOrder order = this.findById(orderId);
        if (BeanUtil.isEmpty(order)) {
            logger.error("用户：{}，用户申请退款失败，查询不到该订单：参数错误", user);
            return ResBean.failed(401, "参数错误");
        }
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isRefund()) {
            logger.error("用户：{}，用户申请退款失败，订单状态不对:{}", user, JSONUtil.toJsonStr(order));
            return ResBean.failed(402, "参数值错误");
        }
        // 设置订单申请退款状态
        order.setOrderStatus(OrderUtil.STATUS_REFUND);

        if (updateWithOptimisticLocker(order) != 1) {
            logger.error("订单申请退款失败:{}", "更新订单信息失败");
            return ResBean.failed(504, "更新数据已经失效");
        }
        // TODO 发送邮件和短信通知，这里采用异步发送，也可以修改为给用户也发送一封邮件
        // 有用户申请退款，邮件通知运营人员
        // notifyService.notifyMail("退款申请", order.toString());
        MailDto mailDto = MailDto.builder()
                .title("退款申请")
                .content(OrderUtil.orderHtmlText(order, order.getId().intValue() + "", null))
                .build();
//        notifyService.notifySslMail(mailDto);
        //给目标用户发邮件
        //notifyService.notifySslMailWithTo(mailDto);

        //创建一个参数集合
        List<WxMaSubscribeMessage.Data> wxMaSubscribeData = new ArrayList<>();
        //第一个内容： 订单号
        WxMaSubscribeMessage.Data wxMaSubscribeData1 = new WxMaSubscribeMessage.Data();
        wxMaSubscribeData1.setName("character_string3");
        wxMaSubscribeData1.setValue(order.getOrderSn());

        //每个参数 存放到大集合中
        wxMaSubscribeData.add(wxMaSubscribeData1);

        //第二个内容： 退款金额
        WxMaSubscribeMessage.Data wxMaSubscribeData2 = new WxMaSubscribeMessage.Data();
        wxMaSubscribeData2.setName("amount2");
        wxMaSubscribeData2.setValue(order.getActualPrice().toString());
        wxMaSubscribeData.add(wxMaSubscribeData2);

        //第三个内容： 客户名称
        WxMaSubscribeMessage.Data wxMaSubscribeData3 = new WxMaSubscribeMessage.Data();
        wxMaSubscribeData3.setName("thing1");
        wxMaSubscribeData3.setValue(user.getNickname());
        wxMaSubscribeData.add(wxMaSubscribeData3);


        try {
            notifyService.sendSubscribeMsg(wxMaSubscribeData, user.getWeixinOpenid(), NotifyType.REFUND);
        } catch (WxErrorException e) {
            logger.error("用户{}:发送微信订阅消息失败：{}", user.getNickname(), e.getMessage());
            e.getStackTrace();
        }
        return ResBean.success("退款成功");
    }
}
