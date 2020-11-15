package com.young.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.young.db.dao.YoungCouponMapper;
import com.young.db.entity.YoungCoupon;
import com.young.db.entity.YoungCouponExample;
import com.young.mall.domain.CouponConstant;
import com.young.mall.exception.Asserts;
import com.young.mall.service.MallCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @Description: 优惠券业务
 * @Author: yqz
 * @CreateDate: 2020/11/14 22:43
 */
@Service
public class MallCouponServiceImpl implements MallCouponService {

    @Autowired
    private YoungCouponMapper youngCouponMapper;

    @Override
    public Optional<List<YoungCoupon>> list(String name, Short type,
                                            Short status, Integer page,
                                            Integer size, String sort,
                                            String order) {

        YoungCouponExample example = new YoungCouponExample();
        YoungCouponExample.Criteria criteria = example.createCriteria();

        if (StrUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (StrUtil.isNotEmpty(sort) && StrUtil.isNotEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        List<YoungCoupon> couponList = youngCouponMapper.selectByExample(example);
        return Optional.ofNullable(couponList);
    }

    @Override
    public Optional<Integer> create(YoungCoupon youngCoupon) {

        youngCoupon.setAddTime(LocalDateTime.now());
        youngCoupon.setUpdateTime(LocalDateTime.now());
        int count = youngCouponMapper.insertSelective(youngCoupon);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<YoungCoupon> findById(Integer id) {
        YoungCoupon youngCoupon = youngCouponMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(youngCoupon);
    }

    @Override
    public Optional<Integer> updateById(YoungCoupon youngCoupon) {
        youngCoupon.setUpdateTime(LocalDateTime.now());
        int count = youngCouponMapper.updateByPrimaryKeySelective(youngCoupon);
        return Optional.ofNullable(count);
    }

    @Override
    public Optional<Integer> delete(Integer id) {
        int count = youngCouponMapper.deleteByPrimaryKey(id);
        return Optional.ofNullable(count);
    }

    @Override
    public String generateCode() {
        String code = getRandomNum(8);
        while (findByCode(code) != null) {
            code = getRandomNum(8);
        }
        return code;
    }

    @Override
    public YoungCoupon findByCode(String code) {

        YoungCouponExample example = new YoungCouponExample();
        example.createCriteria().andCodeEqualTo(code).andTypeEqualTo(CouponConstant.TYPE_CODE)
                .andStatusEqualTo(CouponConstant.STATUS_NORMAL).andDeletedEqualTo(false);
        List<YoungCoupon> couponList = youngCouponMapper.selectByExample(example);
        if (couponList.size() > 1) {
            return couponList.get(0);
        } else if (couponList.size() == 0) {
            return null;
        } else {
            return couponList.get(0);
        }
    }

    private String getRandomNum(Integer num) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        base += "0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
