package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungOrder;
import com.young.db.entity.YoungOrderExample;
import com.young.db.entity.YoungOrderGoods;
import com.young.mall.domain.enums.WxResponseCode;
import com.young.mall.domain.vo.OrderVo;
import com.young.mall.exception.Asserts;
import com.young.mall.express.ExpressService;
import com.young.mall.express.entity.ExpressInfo;
import com.young.mall.express.entity.Traces;
import com.young.mall.service.ClientOrderGoodsService;
import com.young.mall.service.ClientOrderService;
import com.young.mall.service.ClientGrouponService;
import com.young.mall.utils.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单业务
 * @Author: yqz
 * @CreateDate: 2020/12/10 17:29
 */
@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungOrderMapper youngOrderMapper;

    @Autowired
    private ClientGrouponService youngGrouponService;

    @Autowired
    private ClientOrderGoodsService clientOrderGoodsService;

    @Autowired
    private ExpressService expressService;

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
            if (OrderUtil.isCancelStatus(order)) {
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
            Map<String, Object> orderVo = new HashMap<>();
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
            logger.info("{}:获取订单详情失败：{}", userId, WxResponseCode.ORDER_UNKNOWN.getMsg());
            Asserts.fail(WxResponseCode.ORDER_UNKNOWN);
        }
        if (!order.getUserId().equals(userId)) {
            logger.info("{}:获取订单详情失败：{}", userId, WxResponseCode.ORDER_INVALID.getMsg());
            Asserts.fail(WxResponseCode.ORDER_INVALID);
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
            logger.info("{}:获取订单详情失败：{}", userId, WxResponseCode.ORDER_UNKNOWN.getMsg());
            Asserts.fail(WxResponseCode.ORDER_UNKNOWN);
        }
        if (!order.getUserId().equals(userId)) {
            logger.info("{}:获取订单详情失败：{}", userId, WxResponseCode.ORDER_INVALID.getMsg());
            Asserts.fail(WxResponseCode.ORDER_INVALID);
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
                List<Map<String, Object>> traces = new ArrayList<Map<String, Object>>();
                for (Traces trace : eiTrace) {
                    Map<String, Object> traceMap = new HashMap<String, Object>();
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
}
