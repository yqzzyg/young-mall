package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungGoodsExample;
import com.young.db.entity.YoungGoodsProduct;
import com.young.db.entity.YoungGoodsProductExample;
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

    @Override
    public Optional<Integer> count() {

        YoungGoodsProductExample example = new YoungGoodsProductExample();
        example.createCriteria().andDeletedEqualTo(false);
        long count = productMapper.countByExample(example);
        return Optional.ofNullable(((int) count));
    }
}
