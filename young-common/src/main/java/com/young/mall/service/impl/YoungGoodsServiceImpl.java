package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsExample;
import com.young.mall.service.YoungGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description: 商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:39
 */
@Service
public class YoungGoodsServiceImpl implements YoungGoodsService {

    @Autowired
    private YoungGoodsMapper goodsMapper;

    @Override
    public Optional<YoungGoods> findById(Integer id) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIdEqualTo(id).andDeletedEqualTo(false);
        YoungGoods youngGoods = goodsMapper.selectOneByExampleWithBLOBs(example);

        return Optional.ofNullable(youngGoods);
    }
}
