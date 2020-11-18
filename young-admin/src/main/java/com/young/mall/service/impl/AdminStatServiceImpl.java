package com.young.mall.service.impl;

import com.young.db.mapper.StatMapper;
import com.young.mall.service.AdminStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 统计
 * @Author: yqz
 * @CreateDate: 2020/11/18 22:05
 */
@Service
public class AdminStatServiceImpl implements AdminStatService {

    @Autowired
    private StatMapper statMapper;

    @Override
    public Optional<List<Map>> statUser() {

        List<Map> list = statMapper.statUser();

        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<Map>> statOrder() {
        List<Map> list = statMapper.statOrder();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<Map>> statGoods() {
        List<Map> list = statMapper.statGoods();
        return Optional.ofNullable(list);
    }
}
