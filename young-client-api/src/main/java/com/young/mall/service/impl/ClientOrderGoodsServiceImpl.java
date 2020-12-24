package com.young.mall.service.impl;

import com.young.db.dao.YoungOrderGoodsMapper;
import com.young.db.entity.YoungOrderGoods;
import com.young.db.entity.YoungOrderGoodsExample;
import com.young.mall.service.ClientOrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 订单商品
 * @Author: yqz
 * @CreateDate: 2020/12/12 23:05
 */
@Service
public class ClientOrderGoodsServiceImpl implements ClientOrderGoodsService {


    @Resource
    private YoungOrderGoodsMapper youngOrderGoodsMapper;

    @Override
    public List<YoungOrderGoods> queryByOid(Integer orderId) {
        YoungOrderGoodsExample example = new YoungOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return youngOrderGoodsMapper.selectByExample(example);
    }

    @Override
    public List<YoungOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        YoungOrderGoodsExample example = new YoungOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return youngOrderGoodsMapper.selectByExample(example);
    }
}
