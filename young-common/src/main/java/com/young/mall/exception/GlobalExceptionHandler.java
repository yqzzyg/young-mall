package com.young.mall.exception;

import com.young.mall.common.ResBean;
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
    @ResponseBody
    @ExceptionHandler(value = WebApiException.class)
    public ResBean handle(WebApiException e) {
        if (e.getErrorCode() != null) {
            return ResBean.failed(e.getErrorCode());
        }
        return ResBean.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResBean handle(RuntimeException e) {
        if (e.getMessage() != null) {
            return ResBean.failed(e.getMessage());
        }
        return ResBean.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResBean handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ResBean.failed(message);
    }
}
