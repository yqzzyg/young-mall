package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungAdMapper;
import com.young.db.entity.YoungAd;
import com.young.db.entity.YoungAdExample;
import com.young.mall.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 广告业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 18:43
 */
@Service
public class AdvertisingServiceImpl implements AdvertisingService {

    @Autowired
    private YoungAdMapper youngAdMapper;

    @Override
    public Optional<List<YoungAd>> list(String name, String content,
                                        Integer page, Integer size,
                                        String sort, String order) {

        YoungAdExample example = new YoungAdExample();
        YoungAdExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (StrUtil.isNotEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungAd> youngAdList = youngAdMapper.selectByExample(example);

        return Optional.ofNullable(youngAdList);
    }

    @Override
    public Optional<Integer> create(YoungAd youngAd) {
        youngAd.setAddTime(LocalDateTime.now());
        youngAd.setUpdateTime(LocalDateTime.now());
        int count = youngAdMapper.insertSelective(youngAd);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungAd> findById(Integer id) {

        YoungAd youngAd = youngAdMapper.selectByPrimaryKey(id);

        return Optional.ofNullable(youngAd);
    }

    @Override
    public Optional<Integer> update(YoungAd youngAd) {

        youngAd.setUpdateTime(LocalDateTime.now());
        int count = youngAdMapper.updateByPrimaryKeySelective(youngAd);

        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {
        int count = youngAdMapper.logicalDeleteByPrimaryKey(id);
        return Optional.ofNullable(count);
    }
}
