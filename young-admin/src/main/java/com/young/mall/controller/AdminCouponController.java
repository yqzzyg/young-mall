package com.young.mall.controller;

import com.young.mall.common.ResBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 优惠券业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 22:29
 */
@Api(tags = "AdminCouponController", description = "优惠券业务")
@RestController
@RequestMapping("/admin/coupon")
public class AdminCouponController extends BaseController {

    @ApiOperation("分页查询优惠券")
    public ResBean list() {

        return null;
    }
}
