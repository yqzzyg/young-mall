package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungTopicMapper;
import com.young.db.entity.YoungTopic;
import com.young.db.entity.YoungTopicExample;
import com.young.mall.service.MallTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 专题service
 * @Author: yqz
 * @CreateDate: 2020/11/15 22:43
 */
@Service
public class MallTopicServiceImpl implements MallTopicService {

    @Autowired
    private YoungTopicMapper youngTopicMapper;

    @Override
    public Optional<List<YoungTopic>> list(String title, String subtitle,
                                           Integer page, Integer size,
                                           String sort, String order) {

        YoungTopicExample example = new YoungTopicExample();
        YoungTopicExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (StrUtil.isNotEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungTopic> youngTopicList = youngTopicMapper.selectByExample(example);

        return Optional.ofNullable(youngTopicList);
    }

    @Override
    public Optional<Integer> create(YoungTopic youngTopic) {

        youngTopic.setAddTime(LocalDateTime.now());
        youngTopic.setUpdateTime(LocalDateTime.now());
        int count = youngTopicMapper.insertSelective(youngTopic);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungTopic> findById(Integer id) {
        YoungTopicExample example = new YoungTopicExample();
        example.createCriteria().andDeletedEqualTo(false);
        YoungTopic youngTopic = youngTopicMapper.selectOneByExampleWithBLOBs(example);
        return Optional.ofNullable(youngTopic);
    }

    @Override
    public Optional<Integer> update(YoungTopic youngTopic) {

        youngTopic.setUpdateTime(LocalDateTime.now());
        YoungTopicExample example = new YoungTopicExample();
        example.createCriteria().andIdEqualTo(youngTopic.getId());
        int count = youngTopicMapper.updateByExampleSelective(youngTopic, example);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {
        int count = youngTopicMapper.logicalDeleteByPrimaryKey(id);
        return Optional.ofNullable(count);
    }
}
