package com.young.mall.service.impl;

import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.YoungOrder;
import com.young.db.entity.YoungOrderExample;
import com.young.mall.service.ClientOrderService;
import com.young.mall.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
