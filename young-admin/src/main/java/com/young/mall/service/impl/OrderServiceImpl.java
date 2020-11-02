package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungOrderGoodsMapper;
import com.young.db.dao.YoungOrderMapper;
import com.young.db.dao.YoungUserMapper;
import com.young.db.entity.*;
import com.young.mall.dto.UserVo;
import com.young.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private YoungOrderMapper orderMapper;

    @Autowired
    private YoungOrderGoodsMapper goodsMapper;

    @Autowired
    private YoungUserMapper userMapper;

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

        YoungOrderGoodsExample example = new YoungOrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(id).andDeletedEqualTo(false);

        List<YoungOrderGoods> goodsList = goodsMapper.selectByExample(example);

        YoungUser youngUser = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(youngUser, userVo);

        Map<String, Object> map = new HashMap<>(5);

        map.put("order", order);
        map.put("orderGoods", goodsList);
        map.put("user", userVo);

        return Optional.ofNullable(map);
    }
}
