package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungGoodsExample;
import com.young.mall.service.MallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Description: 商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:39
 */
@Service
public class MallGoodsServiceImpl implements MallGoodsService {

    @Autowired
    private YoungGoodsMapper goodsMapper;

    @Override
    public Optional<YoungGoods> findById(Integer id) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIdEqualTo(id).andDeletedEqualTo(false);
        YoungGoods youngGoods = goodsMapper.selectOneByExampleWithBLOBs(example);

        return Optional.ofNullable(youngGoods);
    }

    @Override
    public Optional<YoungGoods> findByGoodsSn(String goodsSn) {
        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andGoodsSnEqualTo(goodsSn).andDeletedEqualTo(false);
        YoungGoods youngGoods = goodsMapper.selectOneByExample(example);
        return Optional.ofNullable(youngGoods);
    }

    @Override
    public Boolean checkExistByName(String name) {
        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andNameEqualTo(name).andDeletedEqualTo(false);
        int count = (int) goodsMapper.countByExample(example);
        return count != 0;
    }

    @Override
    public Integer updateById(YoungGoods youngGoods) {
        youngGoods.setUpdateTime(LocalDateTime.now());
        int count = goodsMapper.updateByPrimaryKeySelective(youngGoods);
        return count;
    }
}
