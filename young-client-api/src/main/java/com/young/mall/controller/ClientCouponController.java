package com.young.mall.controller;

import com.young.db.entity.YoungCoupon;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/26 16:28
 */
@Api(tags = "ClientCouponController")
@RestController
@RequestMapping("/wx/coupon")
public class ClientCouponController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientCouponService clientCouponService;

    @ApiOperation("获取个人可领取优惠券列表")
    @GetMapping("/getUserCoupon")
    public ResBean getUserCoupon(Integer userId) {
        if (userId == null) {
            return ResBean.validateFailed("用户未登录");
        }
        List<YoungCoupon> couponList = clientCouponService.queryAvailableList(userId, 0, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("couponList", couponList);
        return ResBean.success(map);
    }
}
