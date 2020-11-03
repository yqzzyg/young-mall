package com.young.mall.notify;

/**
 * @Description: 通知类型
 * @Author: yqz
 * @CreateDate: 2020/11/3 10:41
 */
public enum NotifyType {
    PAY_SUCCEED("paySucceed"),
    SHIP("ship"),
    // 申请退款
    APPLYREFUND("applyRefund"),
    REFUND("refund"),
    CAPTCHA("captcha");

    private String type;

    NotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
