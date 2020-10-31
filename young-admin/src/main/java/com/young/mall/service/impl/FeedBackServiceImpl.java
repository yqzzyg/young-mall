package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungFeedbackMapper;
import com.young.db.entity.YoungFeedback;
import com.young.db.entity.YoungFeedbackExample;
import com.young.mall.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/10/31 21:12
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private YoungFeedbackMapper feedbackMapper;

    @Override
    public Optional<List<YoungFeedback>> queryFeedBackList(Integer userId, String username, Integer page, Integer size, String sort, String order) {

        YoungFeedbackExample example = new YoungFeedbackExample();
        YoungFeedbackExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (StrUtil.isNotEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }
        criteria.andDeletedEqualTo(false);
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        List<YoungFeedback> feedbackList = feedbackMapper.selectByExample(example);

        return Optional.ofNullable(feedbackList);
    }
}
