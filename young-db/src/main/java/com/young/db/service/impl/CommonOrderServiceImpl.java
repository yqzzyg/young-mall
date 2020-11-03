package com.young.db.service.impl;

import com.young.db.entity.YoungOrder;
import com.young.db.mapper.OrderMapper;
import com.young.db.service.CommonOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 16:29
 */
@Service
public class CommonOrderServiceImpl implements CommonOrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Optional<Integer> updateWithOptimisticLocker(YoungOrder order) {

        LocalDateTime updateTime = order.getUpdateTime();
        order.setUpdateTime(LocalDateTime.now());
        int count = orderMapper.updateWithOptimisticLocker(updateTime, order);

        return Optional.ofNullable(count);
    }
}
