package com.young.mall.service.impl;

import com.young.db.dao.YoungGoodsProductMapper;
import com.young.db.entity.YoungGoodsProduct;
import com.young.db.entity.YoungGoodsProductExample;
import com.young.mall.service.YoungGoodsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 商品货品、库存业务
 * @Author: yqz
 * @CreateDate: 2020/11/10 16:34
 */
@Service
public class YoungGoodsProductServiceImpl implements YoungGoodsProductService {

    @Autowired
    private YoungGoodsProductMapper youngGoodsProductMapper;


    @Override
    public Optional<List<YoungGoodsProduct>> queryByGoodsId(Integer id) {

        YoungGoodsProductExample example = new YoungGoodsProductExample();
        example.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<YoungGoodsProduct> productList = youngGoodsProductMapper.selectByExample(example);

        return Optional.ofNullable(productList);
    }

    @Override
    public Optional<Integer> deleteByGid(Integer gid) {
        YoungGoodsProductExample example = new YoungGoodsProductExample();
        example.createCriteria().andGoodsIdEqualTo(gid);
        int count = youngGoodsProductMapper.logicalDeleteByExample(example);

        return Optional.ofNullable(count);
    }
}
