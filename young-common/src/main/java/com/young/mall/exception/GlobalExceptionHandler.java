package com.young.mall.exception;

import com.young.mall.common.ResBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(value = WebApiException.class)
    public ResBean handle(WebApiException e) {
        logger.info("异常信息：{}", e.getMessage());
        if (e.getErrorCode() != null) {
            return ResBean.failed(e.getErrorCode());
        }
        return ResBean.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResBean handle(RuntimeException e) {
        logger.info("异常信息：{}", e.getMessage());
        if (e.getMessage() != null) {
            return ResBean.failed(e.getMessage());
        }
        return ResBean.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResBean handleValidException(MethodArgumentNotValidException e) {
        logger.info("异常信息：{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return ResBean.failed(message);
    }
}
