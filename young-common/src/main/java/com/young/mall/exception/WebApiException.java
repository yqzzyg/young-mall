package com.young.mall.exception;

import com.young.mall.common.ResErrorCode;

/**
 * @Description: 自定义API异常
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:11
 */
public class WebApiException extends RuntimeException {
    private ResErrorCode resErrorCode;

    public WebApiException(ResErrorCode resErrorCode) {
        super(resErrorCode.getMsg());
        this.resErrorCode = resErrorCode;
    }

    public WebApiException(String msg) {
        super(msg);
    }

    public WebApiException(Throwable cause) {
        super(cause);
    }

    public WebApiException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResErrorCode getErrorCode() {
        return resErrorCode;
    }
}
