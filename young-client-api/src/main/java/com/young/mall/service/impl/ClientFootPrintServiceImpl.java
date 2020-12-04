package com.young.mall.service.impl;

import com.young.db.dao.YoungFootprintMapper;
import com.young.db.entity.YoungFootprint;
import com.young.mall.service.ClientFootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description: 用户足迹
 * @Author: yqz
 * @CreateDate: 2020/12/3 17:48
 */
@Service
public class ClientFootPrintServiceImpl implements ClientFootPrintService {

    @Autowired
    private YoungFootprintMapper youngFootprintMapper;

    @Override
    public int add(YoungFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        int count = youngFootprintMapper.insertSelective(footprint);
        return count;
    }
}
