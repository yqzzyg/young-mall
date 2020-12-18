package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.FeedbackVo;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientFeedbackService;
import com.young.mall.service.ClientUserService;
import com.young.mall.utils.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description: 意见反馈服务
 * @Author: yqz
 * @CreateDate: 2020/12/18 14:54
 */
@Api(tags = "ClientFeedbackController")
@RestController
@RequestMapping("/client/feedback")
public class ClientFeedbackController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientFeedbackService clientFeedbackService;

    @ApiOperation("添加意见反馈")
    @PostMapping("/submit")
    public ResBean submit(@Valid @RequestBody FeedbackVo feedbackVo) {

        ClientUserDetails userDetails = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userDetails)) {
            Asserts.fail("用户未登录");
        }

        if (!RegexUtil.isMobileExact(feedbackVo.getMobile())) {
            Asserts.fail("手机和格式不正确！");
        }
        Integer count = clientFeedbackService.add(userDetails, feedbackVo);

        return ResBean.success(count);
    }

}
