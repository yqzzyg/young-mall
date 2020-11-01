package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungRegionMapper;
import com.young.db.entity.YoungRegion;
import com.young.db.entity.YoungRegionExample;
import com.young.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:21
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private YoungRegionMapper regionMapper;

    @Override
    public Optional<List<YoungRegion>> queryRegionList(String name, Integer code,
                                                       Integer page, Integer size,
                                                       String sort, String order) {

        YoungRegionExample example = new YoungRegionExample();
        YoungRegionExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (code != null) {
            criteria.andCodeEqualTo(code);
        }

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungRegion> regionList = regionMapper.selectByExample(example);
        return Optional.ofNullable(regionList);
    }
}
