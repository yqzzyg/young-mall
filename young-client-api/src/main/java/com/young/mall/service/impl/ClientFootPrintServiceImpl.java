package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungFootprintMapper;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.YoungFootprint;
import com.young.db.entity.YoungFootprintExample;
import com.young.mall.service.ClientFootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:48
 */
@Service
public class ClientFootPrintServiceImpl implements ClientFootPrintService {

    @Autowired
    private YoungFootprintMapper youngFootprintMapper;

    @Autowired
    private YoungGoodsMapper youngGoodsMapper;

    @Override
    public int add(YoungFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        int count = youngFootprintMapper.insertSelective(footprint);
        return count;
    }

    @Override
    public List<YoungFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        YoungFootprintExample example = new YoungFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(YoungFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return youngFootprintMapper.selectByExample(example);
    }

    @Override
    public YoungFootprint findById(Integer id) {
        return youngFootprintMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer deleteById(Integer id) {
        return youngFootprintMapper.logicalDeleteByPrimaryKey(id);
    }
}
