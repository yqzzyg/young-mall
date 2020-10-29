package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungGoodsExample;
import com.young.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:47
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private YoungGoodsMapper goodsMapper;

    @Override
    public Optional<Integer> count() {

        YoungGoodsExample example = new YoungGoodsExample();

        example.createCriteria().andDeletedEqualTo(false);

        long count = goodsMapper.countByExample(example);
        return Optional.ofNullable((int) count);
    }
}
