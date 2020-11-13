package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsSpecificationMapper;
import com.young.db.entity.YoungGoodsSpecification;
import com.young.db.entity.YoungGoodsSpecificationExample;
import com.young.db.mapper.GoodsSpecificationMapper;
import com.young.mall.service.GoodsSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品规格业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 15:46
 */
@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService {

    @Autowired
    private YoungGoodsSpecificationMapper youngGoodsSpecificationMapper;

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Override
    public Optional<List<YoungGoodsSpecification>> queryByGid(Integer id) {
        YoungGoodsSpecificationExample example = new YoungGoodsSpecificationExample();
        example.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);

        List<YoungGoodsSpecification> specificationList = youngGoodsSpecificationMapper.selectByExample(example);
        return Optional.ofNullable(specificationList);
    }

    @Override
    public Optional<Integer> deleteByGid(Integer gid) {
        YoungGoodsSpecificationExample example = new YoungGoodsSpecificationExample();
        example.createCriteria().andGoodsIdEqualTo(gid);

        int count = youngGoodsSpecificationMapper.logicalDeleteByExample(example);

        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> insertList(List<YoungGoodsSpecification> list) {
        for (YoungGoodsSpecification specification : list) {
            specification.setAddTime(LocalDateTime.now());
            specification.setUpdateTime(LocalDateTime.now());
        }
        Integer count = goodsSpecificationMapper.insertList(list);
        return Optional.ofNullable(count);
    }
}
