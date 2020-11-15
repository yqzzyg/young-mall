package com.young.mall.controller;

import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponUser;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.domain.CouponConstant;
import com.young.mall.service.MallCouponService;
import com.young.mall.service.MallCouponUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Autowired
    private MallCouponUserService mallCouponUserService;

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

    @ApiOperation("增加优惠券")
    @PostMapping("/create")
    public ResBean create(@Valid @RequestBody YoungCoupon youngCoupon) {

        if (youngCoupon.getType().equals(CouponConstant.TYPE_CODE)) {
            String code = mallCouponService.generateCode();
            youngCoupon.setCode(code);
        }
        Optional<Integer> optional = mallCouponService.create(youngCoupon);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("创建优惠券失败");
        }
        return ResBean.success("增加优惠券成功");
    }

    @ApiOperation("优惠券详情")
    @GetMapping("/read")
    public ResBean read(@NotNull Integer id) {

        Optional<YoungCoupon> optional = mallCouponService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("查看优惠券详情失败");
        }
        return ResBean.success(optional.get());
    }

    @ApiOperation("更新优惠券")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungCoupon youngCoupon) {

        Optional<Integer> optional = mallCouponService.updateById(youngCoupon);
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success("更新成功");
    }

    @ApiOperation("删除优惠券")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungCoupon youngCoupon) {
        Optional<Integer> optional = mallCouponService.delete(youngCoupon.getId());
        if (!optional.isPresent() || optional.get() != 1) {
            return ResBean.failed("删除失败");
        }
        return ResBean.failed("删除成功");
    }

    @ApiOperation("查询优惠券用户")
    @GetMapping("/userList")
    public ResBean userList(Integer userId, Integer couponId, Short status,
                            @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit,
                            @RequestParam(defaultValue = "add_time") String sort,
                            @RequestParam(defaultValue = "desc") String order) {

        Optional<List<YoungCouponUser>> optional = mallCouponUserService.list(userId, couponId, status, page, limit, sort, order);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        CommonPage<YoungCouponUser> restPage = CommonPage.restPage(optional.get());
        return ResBean.success(restPage);
    }
}
