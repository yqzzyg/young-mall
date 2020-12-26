package com.young.mall.exception;

import com.young.mall.common.ResBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
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
        logger.error("异常信息：{}", e.getMessage());
        if (e.getErrorCode() != null) {
            return ResBean.failed(e.getErrorCode());
        }
        return ResBean.failed(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResBean handleValidException(MethodArgumentNotValidException e) {
        logger.error("异常信息：{}", e.getMessage());
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

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResBean handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return ResBean.validateFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResBean Exception(Exception e) {
        String message = e.getMessage();
        return ResBean.validateFailed(message);
    }
}
