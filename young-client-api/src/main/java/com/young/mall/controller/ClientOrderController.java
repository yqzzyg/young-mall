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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/12/12 22:06
 */
@Api(tags = "ClientOrderController")
@RestController
@RequestMapping("/client/order")
public class ClientOrderController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientOrderService clientOrderService;

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ResBean list(@RequestParam(defaultValue = "0") Integer showType,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.info("用户个人页面数据查询失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Map<String, Object> result = clientOrderService.list(userInfo.getYoungUser().getId(), showType, page, size);

        return ResBean.success(result);
    }
}
