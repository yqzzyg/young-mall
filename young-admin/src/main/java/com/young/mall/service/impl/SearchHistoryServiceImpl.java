package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungSearchHistoryMapper;
import com.young.db.entity.YoungSearchHistory;
import com.young.db.entity.YoungSearchHistoryExample;
import com.young.mall.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 用户搜索历史记录
 * @Author: yqz
 * @CreateDate: 2020/10/31 18:44
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Autowired
    private YoungSearchHistoryMapper searchHistoryMapper;

    @Override
    public Optional<List<YoungSearchHistory>> querySearchHistory(String userId, String keyword, Integer page, Integer size, String sort, String order) {

        YoungSearchHistoryExample example = new YoungSearchHistoryExample();

        YoungSearchHistoryExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (StrUtil.isNotEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungSearchHistory> historyList = searchHistoryMapper.selectByExample(example);
        return Optional.ofNullable(historyList);
    }
}
