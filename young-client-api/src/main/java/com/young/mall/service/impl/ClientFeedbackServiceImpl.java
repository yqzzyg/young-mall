package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.dao.YoungFeedbackMapper;
import com.young.db.entity.YoungFeedback;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.FeedbackVo;
import com.young.mall.service.ClientFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/12/18 15:15
 */
@Service
public class ClientFeedbackServiceImpl implements ClientFeedbackService {

    @Autowired
    private YoungFeedbackMapper youngFeedbackMapper;

    @Override
    public Integer add(ClientUserDetails userDetails, FeedbackVo feedbackVo) {
        Integer userId = userDetails.getYoungUser().getId();

        YoungFeedback feedback = new YoungFeedback();

        BeanUtil.copyProperties(feedbackVo,feedback);
        feedback.setId(null);
        feedback.setUserId(userId);
        feedback.setUsername(userDetails.getUsername());
        // 状态默认是0，1表示状态已发生变化
        feedback.setStatus(1);

        feedback.setUpdateTime(LocalDateTime.now());
        feedback.setAddTime(LocalDateTime.now());
        int count = youngFeedbackMapper.insertSelective(feedback);
        return count;
    }
}
