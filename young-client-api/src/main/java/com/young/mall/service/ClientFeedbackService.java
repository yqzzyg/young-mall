package com.young.mall.service;

import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.FeedbackVo;

/**
 * @Description: 意见反馈
 * @Author: yqz
 * @CreateDate: 2020/12/18 15:12
 */
public interface ClientFeedbackService {

    /**
     * 添加意见反馈
     *
     * @param userDetails
     * @param feedbackVo
     * @return
     */
    Integer add(ClientUserDetails userDetails, FeedbackVo feedbackVo);
}
