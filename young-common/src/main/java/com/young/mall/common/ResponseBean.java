package com.young.mall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 通用返回Bean
 * @Author: yqz
 * @CreateDate: 2020/10/25 15:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean<T> {
    private long code;
    private String msg;
    private T data;
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResponseBean<T> success(T data) {
        return new ResponseBean<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResponseBean<T> success(T data, String message) {
        return new ResponseBean<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param resErrorCode 错误码
     */
    public static <T> ResponseBean<T> failed(ResErrorCode resErrorCode) {
        return new ResponseBean<T>(resErrorCode.getCode(), resErrorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     * @param resErrorCode 错误码
     * @param msg 错误信息
     */
    public static <T> ResponseBean<T> failed(ResErrorCode resErrorCode,String msg) {
        return new ResponseBean<T>(resErrorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     * @param msg 提示信息
     */
    public static <T> ResponseBean<T> failed(String msg) {
        return new ResponseBean<T>(ResponseCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResponseBean<T> failed() {
        return failed(ResponseCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResponseBean<T> validateFailed() {
        return failed(ResponseCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param msg 提示信息
     */
    public static <T> ResponseBean<T> validateFailed(String msg) {
        return new ResponseBean<T>(ResponseCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResponseBean<T> unauthorized(T data) {
        return new ResponseBean<T>(ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResponseBean<T> forbidden(T data) {
        return new ResponseBean<T>(ResponseCode.FORBIDDEN.getCode(), ResponseCode.FORBIDDEN.getMsg(), data);
    }
}
