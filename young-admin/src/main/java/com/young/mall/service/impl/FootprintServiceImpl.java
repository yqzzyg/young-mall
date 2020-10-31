package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungFootprintMapper;
import com.young.db.entity.YoungFootprint;
import com.young.db.entity.YoungFootprintExample;
import com.young.mall.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户足迹service实现类
 * @Author: yqz
 * @CreateDate: 2020/10/31 16:15
 */
@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    private YoungFootprintMapper footprintMapper;

    @Override
    public Optional<List<YoungFootprint>> queryFootPrint(String userId, String goodsId, Integer page, Integer size, String sort, String order) {

        YoungFootprintExample example = new YoungFootprintExample();

        YoungFootprintExample.Criteria criteria = example.createCriteria();


        if (StrUtil.isNotEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (StrUtil.isNotEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page,size);
        List<YoungFootprint> footprintList = footprintMapper.selectByExample(example);
        return Optional.ofNullable(footprintList);
    }
}
