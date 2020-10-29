package com.young.mall.service.impl;

import com.young.db.dao.YoungOrderMapper;
import com.young.db.entity.YoungOrderExample;
import com.young.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 订单数量实现类
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:53
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private YoungOrderMapper orderMapper;

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
}
