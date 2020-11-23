package com.young.mall.service.impl;

import com.young.db.dao.YoungCouponUserMapper;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import com.young.mall.service.ClientCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description: 优惠券用户使用
 * @Author: yqz
 * @CreateDate: 2020/11/23 21:18
 */
@Service
public class ClientCouponUserServiceImpl implements ClientCouponUserService {

    @Autowired
    private YoungCouponUserMapper couponUserMapper;

    @Override
    public Integer countUserAndCoupon(Integer uid, Integer cid) {
        YoungCouponUserExample example = new YoungCouponUserExample();
        example.createCriteria().andUserIdEqualTo(uid).andCouponIdEqualTo(cid).andDeletedEqualTo(false);
        return (int) couponUserMapper.countByExample(example);
    }

    @Override
    public Integer addCouponUser(YoungCouponUser youngCouponUser) {
        youngCouponUser.setAddTime(LocalDateTime.now());
        youngCouponUser.setUpdateTime(LocalDateTime.now());
        return couponUserMapper.insertSelective(youngCouponUser);
    }
}
