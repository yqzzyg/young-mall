package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungCart;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientCartService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Description: 购物车业务
 * @Author: yqz
 * @CreateDate: 2020/11/28 16:46
 */
@Api(tags = "ClientCartController")
@RestController
@RequestMapping("/client/cart")
public class ClientCartController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientCartService clientCartService;

    @ApiOperation("购物车首页")
    @GetMapping("/index")
    public ResBean index() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        return clientCartService.index(userInfo.getYoungUser().getId());
    }

    @ApiOperation("用户购物车数量")
    @GetMapping("/goodscount")
    public ResBean goodsCount() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        List<YoungCart> cartList = clientCartService.queryByUid(userInfo.getYoungUser().getId());
        int goodsCount = 0;
        for (YoungCart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResBean.success(goodsCount);
    }

}
