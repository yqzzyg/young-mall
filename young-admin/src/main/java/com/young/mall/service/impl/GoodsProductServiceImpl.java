package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungGoodsProductExample;
import com.young.db.mapper.GoodsProductMapper;
import com.young.mall.service.GoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 货品service实现类
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:50
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService {

    @Autowired
    private YoungGoodsProductMapper productMapper;

    @Autowired
    private GoodsProductMapper goodsProductMapper;

    @Override
    public Optional<Integer> count() {

        YoungGoodsProductExample example = new YoungGoodsProductExample();
        example.createCriteria().andDeletedEqualTo(false);
        long count = productMapper.countByExample(example);
        return Optional.ofNullable(((int) count));
    }

    @Override
    public Optional<Integer> addStock(Integer id, Short num) {
        int count = goodsProductMapper.addStock(id, num);

        return Optional.ofNullable(count);
    }
}
