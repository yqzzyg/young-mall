package com.young.mall.service.impl;

import com.young.db.entity.YoungOrder;
import com.young.db.mapper.CommonOrderMapper;
import com.young.mall.service.CommonOrderService;
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
    private CommonOrderMapper orderMapper;


    @Override
    public Optional<Integer> updateWithOptimisticLocker(YoungOrder order) {

        LocalDateTime updateTime = order.getUpdateTime();
        order.setUpdateTime(LocalDateTime.now());
        int count = orderMapper.updateWithOptimisticLocker(updateTime, order);

        return Optional.ofNullable(count);
    }
}
