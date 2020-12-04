package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungIssueMapper;
import com.young.db.entity.YoungIssue;
import com.young.db.entity.YoungIssueExample;
import com.young.mall.service.MallIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 通用问题
 * @Author: yqz
 * @CreateDate: 2020/11/8 21:45
 */
@Service
public class MallIssueServiceImpl implements MallIssueService {

    @Autowired
    private YoungIssueMapper issueMapper;

    @Override
    public Optional<List<YoungIssue>> querySelective(String question, Integer page, Integer size, String sort, String order) {

        YoungIssueExample example = new YoungIssueExample();
        YoungIssueExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(question)) {
            criteria.andQuestionEqualTo("%" + question + "%");
        }

        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungIssue> issueList = issueMapper.selectByExample(example);

        return Optional.ofNullable(issueList);
    }

    @Override
    public Optional<Integer> add(YoungIssue issue) {

        issue.setAddTime(LocalDateTime.now());
        issue.setUpdateTime(LocalDateTime.now());

        int count = issueMapper.insertSelective(issue);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungIssue> findById(Integer id) {

        YoungIssue youngIssue = issueMapper.selectByPrimaryKey(id);

        return Optional.ofNullable(youngIssue);
    }

    @Override
    public Optional<Integer> update(YoungIssue issue) {
       issue.setUpdateTime(LocalDateTime.now());

        int count = issueMapper.updateByPrimaryKeySelective(issue);

        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {

        int count = issueMapper.logicalDeleteByPrimaryKey(id);

        return Optional.ofNullable(count);
    }

    @Override
    public List<YoungIssue> query() {
        YoungIssueExample example = new YoungIssueExample();
        example.or().andDeletedEqualTo(false);
        return issueMapper.selectByExample(example);
    }
}
