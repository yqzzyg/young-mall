package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCouponUserMapper;
import com.young.db.entity.YoungCouponUser;
import com.young.db.entity.YoungCouponUserExample;
import com.young.mall.service.MallCouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 优惠券用户查询
 * @Author: yqz
 * @CreateDate: 2020/11/15 21:33
 */
@Service
public class MallCouponUserServiceImpl implements MallCouponUserService {

    @Autowired
    private YoungCouponUserMapper youngCouponUserMapper;

    @Override
    public Optional<List<YoungCouponUser>> list(Integer userId, Integer couponId,
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
        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page,size);
        List<YoungCouponUser> couponUserList = youngCouponUserMapper.selectByExample(example);

        return Optional.ofNullable(couponUserList);
    }
}
