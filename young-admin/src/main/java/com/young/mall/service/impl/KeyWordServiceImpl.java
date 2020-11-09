package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungKeywordMapper;
import com.young.db.entity.YoungKeyword;
import com.young.db.entity.YoungKeywordExample;
import com.young.mall.service.KeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 关键词service
 * @Author: yqz
 * @CreateDate: 2020/11/9 19:52
 */
@Service
public class KeyWordServiceImpl implements KeyWordService {

    @Autowired
    private YoungKeywordMapper keywordMapper;

    @Override
    public Optional<List<YoungKeyword>> querySelective(String keyword, String url,
                                                       Integer page, Integer size,
                                                       String sort, String order) {
        YoungKeywordExample example = new YoungKeywordExample();
        YoungKeywordExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (StrUtil.isNotEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungKeyword> keywordList = keywordMapper.selectByExample(example);
        return Optional.ofNullable(keywordList);
    }

    @Override
    public Optional<Integer> create(YoungKeyword keyword) {

        keyword.setAddTime(LocalDateTime.now());
        keyword.setUpdateTime(LocalDateTime.now());

        int count = keywordMapper.insertSelective(keyword);
        return Optional.ofNullable(count);
    }


    @Override
    public Optional<YoungKeyword> findById(Integer id) {
        YoungKeyword youngKeyword = keywordMapper.selectByPrimaryKey(id);

        return Optional.ofNullable(youngKeyword);
    }


    @Override
    public Optional<Integer> updateById(YoungKeyword keyword) {

        keyword.setUpdateTime(LocalDateTime.now());
        int count = keywordMapper.updateByPrimaryKeySelective(keyword);

        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {

        int count = keywordMapper.logicalDeleteByPrimaryKey(id);

        return Optional.ofNullable(count);
    }
}
