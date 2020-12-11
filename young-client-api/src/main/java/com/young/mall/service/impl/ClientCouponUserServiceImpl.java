package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCouponUserMapper;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import com.young.mall.service.ClientCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<YoungCouponUser> queryList(Integer userId, Integer couponId,
                                           Short status,
                                           Integer page, Integer size,
                                           String sort, String order) {
        YoungCouponUserExample example = new YoungCouponUserExample();
        YoungCouponUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (couponId != null) {
            criteria.andCouponIdEqualTo(couponId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(size)) {
            PageHelper.startPage(page, size);
        }

        return couponUserMapper.selectByExample(example);
    }
}
