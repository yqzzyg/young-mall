package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.*;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.enums.ClientResponseCode;
import com.young.mall.domain.vo.OrderCommentVo;
import com.young.mall.domain.vo.OrderVo;
import com.young.mall.exception.Asserts;
import com.young.mall.express.ExpressService;
import com.young.mall.express.entity.ExpressInfo;
import com.young.mall.express.entity.Traces;
import com.young.mall.service.*;
import com.young.mall.utils.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
        if (!hasPicture || hasPicture == null) {
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
}
