package com.young.mall.exception;

import com.young.mall.common.ResErrorCode;

/**
 * @Description: 自定义断言处理类，用于抛出各种API异常
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:16
 */
public class Asserts {
    public static void fail(String msg) {
        throw new WebApiException(msg);
    }

    public static void fail(ResErrorCode resErrorCode) {
        throw new WebApiException(resErrorCode);
    }

    public static void state(boolean expression, String message) {
        if (expression) {
            throw new WebApiException(message);
        }
    }
}
