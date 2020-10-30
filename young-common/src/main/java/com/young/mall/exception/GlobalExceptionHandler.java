package com.young.mall.exception;

import com.young.mall.common.ResBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常
 * @Author: yqz
 * @CreateDate: 2020/10/29 22:27
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = WebApiException.class)
    public ResBean handle(WebApiException e) {
        if (e.getErrorCode() != null) {
            return ResBean.failed(e.getErrorCode());
        }
        return ResBean.failed(e.getMessage());
    }
}
