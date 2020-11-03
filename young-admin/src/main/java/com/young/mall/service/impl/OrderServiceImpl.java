package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungOrderGoodsMapper;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.*;
import com.young.db.service.CommonOrderService;
import com.young.mall.common.ResBean;
import com.young.mall.dto.UserVo;
import com.young.mall.exception.Asserts;
import com.young.mall.service.GoodsProductService;
import com.young.mall.service.OrderService;
import com.young.mall.utils.AdminResponseCode;
import com.young.mall.utils.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 订单数量实现类
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:53
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungOrderMapper orderMapper;

    @Autowired
    private YoungOrderGoodsMapper goodsMapper;

    @Autowired
    private YoungUserMapper userMapper;

    @Autowired
    private CommonOrderService commonOrderService;
    @Autowired
    private GoodsProductService goodsProductService;

    @Override
    public Optional<Integer> count(Integer userId) {

        YoungOrderExample example = new YoungOrderExample();
        example.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        long count = orderMapper.countByExample(example);
        return Optional.ofNullable(((int) count));
    }

    @Override
    public Optional<Integer> count() {

        YoungOrderExample example = new YoungOrderExample();

        example.createCriteria().andDeletedEqualTo(false);

        long count = orderMapper.countByExample(example);

        return Optional.ofNullable(((int) count));
    }

    @Override
    public Optional<List<YoungOrder>> list(Integer userId, String orderSn,
                                           List<Short> orderStatusArray,
                                           Integer page, Integer size,
                                           String sort, String order) {

        YoungOrderExample example = new YoungOrderExample();
        YoungOrderExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StrUtil.isNotBlank(orderSn)) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if (CollectionUtil.isNotEmpty(orderStatusArray)) {
            criteria.andOrderStatusIn(orderStatusArray);
        }
        criteria.andDeletedEqualTo(false);

        if (!StrUtil.isNotBlank(sort) && !StrUtil.isNotBlank(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungOrder> youngOrders = orderMapper.selectByExample(example);
        return Optional.ofNullable(youngOrders);
    }

    @Override
    public Optional<Map<String, Object>> detail(Integer id) {

        YoungOrder order = orderMapper.selectByPrimaryKey(id);

        List<YoungOrderGoods> goodsList = queryByOid(id);
        YoungUser youngUser = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(youngUser, userVo);

        Map<String, Object> map = new HashMap<>(5);

        map.put("order", order);
        map.put("orderGoods", goodsList);
        map.put("user", userVo);

        return Optional.ofNullable(map);
    }

    private List<YoungOrderGoods> queryByOid(Integer id) {
        YoungOrderGoodsExample example = new YoungOrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(id).andDeletedEqualTo(false);

        List<YoungOrderGoods> goodsList = goodsMapper.selectByExample(example);
        return goodsList;
    }

    @Transactional
    @Override
    public ResBean refund(Map<String, Object> map) {
        JSONObject paramMap = JSONUtil.parseObj(map);
        Integer orderId = paramMap.getInt("orderId");
        String refundMoney = paramMap.getStr("refundMoney");
        if (orderId == null) {
            return ResBean.validateFailed("订单ID不能为空");
        }
        if (StrUtil.isNotBlank(refundMoney)) {
            return ResBean.validateFailed("退款jine不能为空");
        }

        YoungOrder youngOrder = orderMapper.selectByPrimaryKey(orderId);
        if (BeanUtil.isEmpty(youngOrder)) {
            return ResBean.validateFailed("查无此订单");
        }

        if (youngOrder.getActualPrice().compareTo(new BigDecimal(refundMoney)) != 0) {
            return ResBean.validateFailed("退款金额与订单金额不一致");
        }
        //判断订单状态
        if (!youngOrder.getOrderStatus().equals(OrderUtil.STATUS_REFUND)) {
            logger.info("商场管理->订单管理->订单退款失败:{}", AdminResponseCode.ORDER_REFUND_FAILED.desc());
            return ResBean.failed(AdminResponseCode.ORDER_REFUND_FAILED.code(), AdminResponseCode.ORDER_REFUND_FAILED.desc())
        }
        //微信退款
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutTradeNo(youngOrder.getOrderSn());
        wxPayRefundRequest.setOutRefundNo("refund_" + youngOrder.getOrderStatus());
        //元 转 分
        int totalFee = youngOrder.getActualPrice().multiply(new BigDecimal(100)).intValue();
        wxPayRefundRequest.setTotalFee(totalFee);
        wxPayRefundRequest.setRefundFee(totalFee);
        /**
         * 为了账号安全，暂时屏蔽api退款 WxPayRefundResult wxPayRefundResult = null; try {
         * wxPayRefundResult = wxPayService.refund(wxPayRefundRequest); } catch
         * (WxPayException e) { e.printStackTrace(); return
         * ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败"); } if
         * (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) { logger.warn("refund
         * fail: " + wxPayRefundResult.getReturnMsg()); return
         * ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败"); } if
         * (!wxPayRefundResult.getResultCode().equals("SUCCESS")) { logger.warn("refund
         * fail: " + wxPayRefundResult.getReturnMsg()); return
         * ResponseUtil.fail(ORDER_REFUND_FAILED, "订单退款失败"); }
         */
        //设置订单取消状态
        youngOrder.setOrderStatus(OrderUtil.STATUS_REFUND_CONFIRM);
        if (commonOrderService.updateWithOptimisticLocker(youngOrder).isPresent()) {
            logger.info("商场管理->订单管理->订单退款失败:{}", "更新数据已失效");
            Asserts.fail("更新数据失败");
        }
        //商品货品数量增加
        List<YoungOrderGoods> goodsList = queryByOid(orderId);
        for (YoungOrderGoods orderGoods : goodsList) {
            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();
            Optional<Integer> optional = goodsProductService.addStock(productId, number);
            if (optional.isPresent()) {
                logger.info("商场管理->订单管理->订单退款失败:{}", "商品货品库存增加失败");
                Asserts.fail("商品库存增加失败");
            }
        }

        // TODO 发送邮件和短信通知，这里采用异步发送
        // 退款成功通知用户, 例如“您申请的订单退款 [ 单号:{1} ] 已成功，请耐心等待到账。”
        // 注意订单号只发后6位

        return null;
    }
}
