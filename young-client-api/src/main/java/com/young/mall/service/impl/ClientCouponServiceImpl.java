package com.young.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCouponMapper;
import com.young.db.dao.YoungCouponUserMapper;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCoupon.*;
import com.young.db.entity.YoungCouponExample;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import com.young.mall.common.ResBean;
import com.young.mall.domain.CouponConstant;
import com.young.mall.service.ClientCouponService;
import com.young.mall.service.ClientCouponUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private ClientCouponUserService clientCouponUserService;

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

        YoungCouponExample example = new YoungCouponExample();
        YoungCouponExample.Criteria criteria = example.createCriteria();
        //如果该用户领过优惠券，则根据优惠券id过滤掉
        if (!CollectionUtil.isEmpty(couponUserList)) {
            List<Integer> couponIds = couponUserList.stream().map(youngCouponUser -> youngCouponUser.getCouponId()).collect(Collectors.toList());
            criteria.andIdNotIn(couponIds).andDeletedEqualTo(false);
        }
        criteria.andDeletedEqualTo(false);

        example.setOrderByClause("add_time desc");

        PageHelper.startPage(offset, limit);
        List<YoungCoupon> couponList = youngCouponMapper.selectByExampleSelective(example, result);
        return couponList;
    }

    @Override
    public YoungCoupon findById(Integer id) {
        return youngCouponMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public ResBean receiveAll(Integer userId) {
        List<YoungCoupon> couponList = queryAvailableList(userId, 0, 10);
        if (CollectionUtil.isEmpty(couponList)) {
            return ResBean.failed("没有可领取的优惠券");
        }
        for (YoungCoupon coupon : couponList) {
            Integer couponId = coupon.getId();
            YoungCoupon youngCoupon = findById(couponId);
            if (BeanUtil.isEmpty(youngCoupon)) {
                continue;
            }
            // 优惠券分发类型 例如兑换券类型的优惠券不能领取
            Short type = youngCoupon.getType();
            // 优惠券状态，已下架或者过期不能领取
            Short status = youngCoupon.getStatus();
            if (CouponConstant.TYPE_CODE.equals(type) || CouponConstant.STATUS_OUT.equals(status)) {
                continue;
            }
            //用户领券记录，记录到young_coupon_user表中
            YoungCouponUser couponUser = new YoungCouponUser();
            couponUser.setCouponId(couponId);
            couponUser.setUserId(userId);
            Short timeType = coupon.getTimeType();
            if (CouponConstant.TIME_TYPE_TIME.equals(timeType)) {
                couponUser.setStartTime(coupon.getStartTime());
                couponUser.setEndTime(coupon.getEndTime());
            } else {
                LocalDate now = LocalDate.now();
                couponUser.setStartTime(now);
                couponUser.setEndTime(now.plusDays(coupon.getDays()));
            }
            Integer count = clientCouponUserService.addCouponUser(couponUser);
        }
        return ResBean.success("一键领取成功");
    }

    @Override
    public int queryUserCouponCnt(Integer userId) {
        YoungCouponUserExample example = new YoungCouponUserExample();
        YoungCouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);
        return (int) youngCouponUserMapper.countByExample(example);
    }
}
