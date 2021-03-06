package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 微信小程序首页
 * @Author: yqz
 * @CreateDate: 2020/11/21 20:24
 */
@Api(tags = "ClientHomeController", description = "微信首页")
@RestController
@RequestMapping("/client/home")
public class ClientHomeController {

    @Autowired
    private WxHomeService wxHomeService;

    @Autowired
    private ClientUserService clientUserService;

    @ApiOperation("查询客户端主页展示数据")
    @GetMapping("/index")
    public ResBean<Map<String, Object>> index() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();

        Integer userId = null;
        if (!BeanUtil.isEmpty(userInfo)) {
            userId = userInfo.getYoungUser().getId();
        }
        Map<String, Object> homeIndexData = wxHomeService.getIndexData(userId);
        return ResBean.success(homeIndexData);
    }

}
