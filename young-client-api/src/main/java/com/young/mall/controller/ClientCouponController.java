package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungUser;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.vo.CouponVo;
import com.young.mall.exception.Asserts;
import com.young.mall.service.ClientCouponService;
import com.young.mall.service.ClientCouponUserService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
@RequestMapping("/client/coupon")
public class ClientCouponController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientCouponService clientCouponService;

    @Autowired
    private ClientCouponUserService clientCouponUserService;

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

    @ApiOperation("个人优惠券列表")
    @GetMapping("/myList")
    public ResBean myList(@NotNull @RequestParam("status") Short status,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer size,
                          @RequestParam(defaultValue = "add_time") String sort,
                          @RequestParam(defaultValue = "desc") String order) {

        logger.error("个人优惠券列表接口入参：status:{},page:{},size:{},sort:{},order:{}", status, page, size, sort, order);
        ClientUserDetails userDetails = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userDetails)) {
            Asserts.fail("用户未登录");
        }

        List<YoungCouponUser> couponUserList = clientCouponUserService.queryList(userDetails.getYoungUser().getId(), null,
                status, page, size, sort, order);
        List<CouponVo> couponVoList = change(couponUserList);
        long total = PageInfo.of(couponUserList).getTotal();

        Map<String, Object> data = new HashMap<>();
        data.put("data", couponVoList);
        data.put("count", total);
        return ResBean.success(data);
    }

    private List<CouponVo> change(List<YoungCouponUser> couponList) {

        List<CouponVo> couponVoList = new ArrayList<>(couponList.size());

        for (YoungCouponUser couponUser : couponList) {
            Integer couponId = couponUser.getCouponId();
            YoungCoupon coupon = clientCouponService.findById(couponId);
            CouponVo couponVo = new CouponVo();
            couponVo.setId(coupon.getId());
            couponVo.setName(coupon.getName());
            couponVo.setDesc(coupon.getDesc());
            couponVo.setTag(coupon.getTag());
            couponVo.setMin(coupon.getMin().toPlainString());
            couponVo.setDiscount(coupon.getDiscount().toPlainString());
            couponVo.setStartTime(couponUser.getStartTime());
            couponVo.setEndTime(couponUser.getEndTime());
            couponVoList.add(couponVo);
        }

        return couponVoList;
    }
}
