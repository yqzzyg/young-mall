package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsAttributeMapper;
import com.young.db.entity.YoungGoodsAttribute;
import com.young.db.entity.YoungGoodsAttributeExample;
import com.young.mall.service.ClientGoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 商品属性
 * @Author: yqz
 * @CreateDate: 2020/12/2 21:12
 */
@Service
public class ClientGoodsAttributeServiceImpl implements ClientGoodsAttributeService {

    @Autowired
    private YoungGoodsAttributeMapper youngGoodsAttributeMapper;

    @Override
    public List<YoungGoodsAttribute> queryByGid(Integer goodsId) {

        YoungGoodsAttributeExample example = new YoungGoodsAttributeExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<YoungGoodsAttribute> attributeList = youngGoodsAttributeMapper.selectByExample(example);

        return attributeList;
    }
}
