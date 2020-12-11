package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungUserAccount;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientCouponService;
import com.young.mall.service.ClientOrderService;
import com.young.mall.service.ClientUserService;
import com.young.mall.service.MallAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @Autowired
    private MallAccountService mallAccountService;

    @Autowired
    private ClientCouponService clientCouponService;

    @ApiOperation("用户个人页面数据")
    @GetMapping("/index")
    public ResBean index() {
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            logger.info("用户个人页面数据查询失败，未登录。");
            return ResBean.unauthorized("请登录！");
        }
        Map<String, Object> data = new HashMap<>(6);

        data.put("order", clientOrderService.orderInfo(userInfo.getYoungUser().getId()));
        // 查询用户账号的总金额和剩余金额
        YoungUserAccount userAccount = mallAccountService.findShareUserAccountByUserId(userInfo.getYoungUser().getId());
        BigDecimal totalAmount = new BigDecimal("0.00");
        BigDecimal remainAmount = new BigDecimal("0.00");

        if (!BeanUtil.isEmpty(userAccount)) {
            totalAmount = userAccount.getTotalAmount();
            remainAmount = userAccount.getRemainAmount();
        }
        data.put("totalAmount", totalAmount);
        data.put("remainAmount", remainAmount);

        // 查询用户的优惠券
        int count = clientCouponService.queryUserCouponCnt(userInfo.getYoungUser().getId());
        data.put("couponCount", count);
        return ResBean.success(data);
    }
}
