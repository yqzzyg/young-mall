package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.CouponConstant;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientCouponService;
import com.young.mall.service.ClientCouponUserService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
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


    @Autowired
    private ClientUserService clientUserService;

    @ApiOperation("获取个人可领取优惠券列表")
    @GetMapping("/getUserCoupon")
    public ResBean<Map<String, Object>> getUserCoupon() {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.validateFailed("用户未登录");
        }
        YoungUser youngUser = userInfo.getYoungUser();
        List<YoungCoupon> couponList = clientCouponService.queryAvailableList(youngUser.getId(), 0, 10);
        Map<String, Object> map = new HashMap<>();
        map.put("couponList", couponList);
        return ResBean.success(map);
    }

    @ApiOperation("一键领取优惠券")
    @PostMapping("receiveAll")
    public ResBean receiveAll() {
        ClientUserDetails userDetails = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userDetails)) {
            Asserts.fail("用户未登录");
        }
        YoungUser user = userDetails.getYoungUser();
        Integer userId = user.getId();
        return clientCouponService.receiveAll(userId);
    }
}
