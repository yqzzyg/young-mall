package com.young.mall.service.impl;

import com.young.db.dao.YoungCouponMapper;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponExample;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungUser;
import com.young.mall.domain.CouponConstant;
import com.young.mall.service.ClientCouponAssignService;
import com.young.mall.service.ClientCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @Description: 客户端优惠券相关
 * @Author: yqz
 * @CreateDate: 2020/11/23 21:05
 */
@Service
public class ClientCouponAssignServiceImpl implements ClientCouponAssignService {

    @Autowired
    private YoungCouponMapper youngCouponMapper;

    @Autowired
    private ClientCouponUserService clientCouponUserService;

    @Override
    public void assignForRegister(Integer userId) {
        List<YoungCoupon> couponList = getRegister();
        for (YoungCoupon coupon : couponList) {
            Integer couponId = coupon.getId();
            Integer count = clientCouponUserService.countUserAndCoupon(userId, couponId);
            //如果大于 0 说明这个优惠券该用户已经使用过
            if (count > 0) {
                continue;
            }
            Short limit = coupon.getLimit();
            while (limit > 0) {
                YoungCouponUser couponUser = new YoungCouponUser();
                couponUser.setCouponId(couponId);
                couponUser.setUserId(userId);
                //优惠券有效期类型
                //有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；
                Short timeType = coupon.getTimeType();
                if (timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
                    couponUser.setStartTime(coupon.getStartTime());
                    couponUser.setEndTime(coupon.getEndTime());
                } else {
                    LocalDate now = LocalDate.now();
                    couponUser.setStartTime(now);
                    couponUser.setEndTime(now.plusDays(coupon.getDays()));
                }
                Integer couponUserCount = clientCouponUserService.addCouponUser(couponUser);
                limit--;
            }
        }
    }

    /**
     * 查询新用户注册优惠券
     *
     * @return
     */
    List<YoungCoupon> getRegister() {
        YoungCouponExample example = new YoungCouponExample();
        example.createCriteria()
                .andTypeEqualTo(CouponConstant.TYPE_REGISTER)
                .andStatusEqualTo(CouponConstant.STATUS_NORMAL)
                .andDeletedEqualTo(false);
        return youngCouponMapper.selectByExample(example);
    }
}
