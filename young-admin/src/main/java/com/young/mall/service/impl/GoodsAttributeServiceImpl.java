package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsAttributeMapper;
import com.young.db.entity.YoungGoodsAttribute;
import com.young.db.entity.YoungGoodsAttributeExample;
import com.young.db.mapper.GoodsAttributeMapper;
import com.young.mall.service.GoodsAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品参数业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 16:02
 */
@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

    @Autowired
    private YoungGoodsAttributeMapper youngGoodsAttributeMapper;

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public Optional<List<YoungGoodsAttribute>> queryByGoodsId(Integer id) {

        YoungGoodsAttributeExample example = new YoungGoodsAttributeExample();
        example.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<YoungGoodsAttribute> attributeList = youngGoodsAttributeMapper.selectByExample(example);

        return Optional.ofNullable(attributeList);
    }

    @Override
    public Optional<Integer> delete(Integer gid) {
        YoungGoodsAttributeExample example = new YoungGoodsAttributeExample();
        example.createCriteria().andGoodsIdEqualTo(gid);

        int count = youngGoodsAttributeMapper.logicalDeleteByExample(example);

        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> insertList(List<YoungGoodsAttribute> list) {
        for (YoungGoodsAttribute attribute : list) {
            attribute.setAddTime(LocalDateTime.now());
            attribute.setUpdateTime(LocalDateTime.now());
        }
        Integer count = goodsAttributeMapper.insertList(list);
        return Optional.ofNullable(count);
    }
}
