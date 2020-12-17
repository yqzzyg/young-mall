package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.YoungGroupon;
import com.young.db.entity.YoungOrder;
import com.young.db.entity.YoungOrderExample;
import com.young.db.entity.YoungOrderGoods;
import com.young.mall.service.ClientOrderGoodsService;
import com.young.mall.service.ClientOrderService;
import com.young.mall.service.ClientGrouponService;
import com.young.mall.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private YoungOrderMapper youngOrderMapper;

    @Autowired
    private ClientGrouponService youngGrouponService;

    @Autowired
    private ClientOrderGoodsService clientOrderGoodsService;


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
}
