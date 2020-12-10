package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientOrderService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/10 11:30
 */
@Api(tags = "ClientUserController")
@RestController
@RequestMapping("/client/user")
public class ClientUserController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientOrderService clientOrderService;

    @ApiOperation("用户个人页面数据")
    @GetMapping("/index")
    public ResBean index() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.info("用户个人页面数据查询失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Map<String, Object> data = new HashMap<>();

        data.put("order", clientOrderService.orderInfo(userInfo.getYoungUser().getId()));

        return ResBean.success(data);
    }
}
