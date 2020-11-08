package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungIssueMapper;
import com.young.db.entity.YoungIssue;
import com.young.db.entity.YoungIssueExample;
import com.young.mall.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 通用问题
 * @Author: yqz
 * @CreateDate: 2020/11/8 21:45
 */
@Service
public class IssueServiceImpl implements IssueService {

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
}
