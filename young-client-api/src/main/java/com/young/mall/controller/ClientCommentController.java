package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungComment;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 用户评论服务
 * @Author: yqz
 * @CreateDate: 2020/12/23 15:10
 */
@Api(tags = "ClientCommentController")
@RestController
@RequestMapping("/client/comment")
public class ClientCommentController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ClientUserService clientUserService;

    @ApiOperation("发表评论")
    @PostMapping("/post")
    public ResBean PostComment(@RequestBody YoungComment comment) {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.info("发表评判失败：用户未登录");
            return ResBean.validateFailed("用户未登录");
        }

        return null;
    }

}
