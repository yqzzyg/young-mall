package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungStorageMapper;
import com.young.db.entity.YoungStorage;
import com.young.db.entity.YoungStorageExample;
import com.young.mall.service.MallStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 对象存储service
 * @Author: yqz
 * @CreateDate: 2020/11/5 15:40
 */
@Service
public class StorageServiceImpl implements MallStorageService {

    @Autowired
    private YoungStorageMapper storageMapper;

    @Override
    public Optional<Integer> deleteByKey(String key) {
        YoungStorageExample example = new YoungStorageExample();
        example.createCriteria().andKeyEqualTo(key);
        int count = storageMapper.logicalDeleteByExample(example);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> add(YoungStorage storageInfo) {
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setUpdateTime(LocalDateTime.now());
        int count = storageMapper.insertSelective(storageInfo);
        return Optional.ofNullable(count);
    }

    @Override
    public YoungStorage findByKey(String key) {

        YoungStorageExample example = new YoungStorageExample();
        example.createCriteria().andKeyEqualTo(key).andDeletedEqualTo(false);
        YoungStorage youngStorage = storageMapper.selectOneByExample(example);
        return youngStorage;
    }

    @Override
    public Optional<Integer> update(YoungStorage storageInfo) {

        storageInfo.setUpdateTime(LocalDateTime.now());
        int count = storageMapper.updateByPrimaryKeySelective(storageInfo);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungStorage> findById(Integer id) {
        YoungStorage youngStorage = storageMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(youngStorage);
    }

    @Override
    public Optional<List<YoungStorage>> querySelective(String key, String name,
                                                       Integer page, Integer size,
                                                       String sort, String order) {

        YoungStorageExample example = new YoungStorageExample();
        YoungStorageExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungStorage> storageList = storageMapper.selectByExample(example);
        return Optional.ofNullable(storageList);
    }
}
