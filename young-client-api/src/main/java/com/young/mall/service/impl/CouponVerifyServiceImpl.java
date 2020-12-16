package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponUser;
import com.young.mall.domain.CouponConstant;
import com.young.mall.service.ClientCouponService;
import com.young.mall.service.ClientCouponUserService;
import com.young.mall.service.CouponVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description: 检测优惠券
 * @Author: yqz
 * @CreateDate: 2020/12/16 16:33
 */
@Service
public class CouponVerifyServiceImpl implements CouponVerifyService {


    @Autowired
    private ClientCouponService clientCouponService;

    @Autowired
    private ClientCouponUserService clientCouponUserService;

    @Override
    public YoungCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice) {

        YoungCoupon coupon = clientCouponService.findById(userId);

        YoungCouponUser couponUser = clientCouponUserService.queryOne(userId, couponId);

        if (BeanUtil.isEmpty(coupon) || BeanUtil.isEmpty(couponUser)) {
            return null;
        }
        // 检查是否超期
        Short timeType = coupon.getTimeType();
        Short days = coupon.getDays();
        LocalDate now = LocalDate.now();
        if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if (now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        } else if (timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expired = couponUser.getAddTime().plusDays(days);
            if (LocalDateTime.now().isAfter(expired)) {
                return null;
            }
        } else {
            return null;
        }
        // 检测商品是否符合
        // TODO 目前仅支持全平台商品，所以不需要检测
        Short goodType = coupon.getGoodsType();
        if (!goodType.equals(CouponConstant.GOODS_TYPE_ALL)) {
            return null;
        }
        // 检测订单状态
        Short status = coupon.getStatus();
        if (!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }
        // 检测是否满足最低消费
        if (checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }

        return coupon;
    }
}
