package com.young.mall.controller;

import com.young.db.entity.YoungCoupon;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.MallCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 优惠券业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 22:29
 */
@Api(tags = "AdminCouponController", description = "优惠券业务")
@RestController
@RequestMapping("/admin/coupon")
public class AdminCouponController extends BaseController {

    @Autowired
    private MallCouponService mallCouponService;

    @ApiOperation("分页查询优惠券")
    @GetMapping("/list")
    public ResBean list(String name, Short type,
                        Short status, @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("name:{},type:{},status:{},page:{},size:{},sort:{},order:{}",
                name, type, status, page, size, sort, order);
        Optional<List<YoungCoupon>> optional = mallCouponService.list(name, type, status, page, size, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        List<YoungCoupon> couponList = optional.get();
        CommonPage<YoungCoupon> restPage = CommonPage.restPage(couponList);
        return ResBean.success(restPage);
    }
}
