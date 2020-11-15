package com.young.mall.domain;

/**
 * @Description: 优惠券
 * 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换；
 * @Author: yqz
 * @CreateDate: 2020/11/15 21:08
 */
public class CouponConstant {
    public static final Short TYPE_COMMON = 0;
    public static final Short TYPE_REGISTER = 1;
    public static final Short TYPE_CODE = 2;

    public static final Short GOODS_TYPE_ALL = 0;
    public static final Short GOODS_TYPE_CATEGORY = 1;
    public static final Short GOODS_TYPE_ARRAY = 2;

    public static final Short STATUS_NORMAL = 0;
    public static final Short STATUS_EXPIRED = 1;
    public static final Short STATUS_OUT = 2;

    public static final Short TIME_TYPE_DAYS = 0;
    public static final Short TIME_TYPE_TIME = 1;
}
