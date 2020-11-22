package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCouponMapper;
import com.young.db.dao.YoungCouponUserMapper;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCoupon.*;
import com.young.db.entity.YoungCouponExample;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import com.young.mall.domain.CouponConstant;
import com.young.mall.service.ClientCouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 优惠券
 * @Author: yqz
 * @CreateDate: 2020/11/22 10:54
 */
@Service
public class ClientCouponServiceImpl implements ClientCouponService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private YoungCouponMapper youngCouponMapper;

    @Autowired
    private YoungCouponUserMapper youngCouponUserMapper;

    private Column[] result = new Column[]{Column.id, Column.name, Column.desc, Column.tag,
            Column.days, Column.startTime, Column.endTime,
            Column.discount, Column.min};

    @Override
    public List<YoungCoupon> queryList(int offset, int limit) {

        YoungCouponExample example = new YoungCouponExample();
        example.createCriteria().andTypeEqualTo(CouponConstant.TYPE_COMMON)
                .andStatusEqualTo(CouponConstant.STATUS_NORMAL)
                .andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);
        List<YoungCoupon> couponList = youngCouponMapper.selectByExampleSelective(example, result);
        return couponList;
    }

    @Override
    public List<YoungCoupon> queryAvailableList(Integer userId, int offset, int limit) {
        // 过滤掉登录账号已经领取过的coupon
        List<YoungCouponUser> couponUserList = youngCouponUserMapper.selectByExample(
                YoungCouponUserExample.newAndCreateCriteria().andUserIdEqualTo(userId).example()
        );

        List<Integer> couponIds = couponUserList.stream().map(youngCouponUser -> youngCouponUser.getCouponId()).collect(Collectors.toList());
        YoungCouponExample example = new YoungCouponExample();
        example.createCriteria().andIdNotIn(couponIds).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");

        PageHelper.startPage(offset, limit);
        List<YoungCoupon> couponList = youngCouponMapper.selectByExampleSelective(example, result);
        return couponList;
    }
}
