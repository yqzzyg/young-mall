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
public class ResBean<T> {
    private long code;
    private String msg;
    private T data;

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResBean<T> success(T data) {
        return new ResBean<T>(ResCode.SUCCESS.getCode(), ResCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> ResBean<T> success(T data, String message) {
        return new ResBean<T>(ResCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param resErrorCode 错误码
     */
    public static <T> ResBean<T> failed(ResErrorCode resErrorCode) {
        return new ResBean<T>(resErrorCode.getCode(), resErrorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param resErrorCode 错误码
     * @param msg          错误信息
     */
    public static <T> ResBean<T> failed(ResErrorCode resErrorCode, String msg) {
        return new ResBean<T>(resErrorCode.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> ResBean<T> failed(String msg) {
        return new ResBean<T>(ResCode.FAILED.getCode(), msg, null);
    }

    /**
     * 失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> ResBean<T> failed(long code, String msg) {
        return new ResBean<T>(code, msg, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResBean<T> failed() {
        return failed(ResCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResBean<T> validateFailed() {
        return failed(ResCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param msg 提示信息
     */
    public static <T> ResBean<T> validateFailed(String msg) {
        return new ResBean<T>(ResCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResBean<T> unauthorized(T data) {
        return new ResBean<T>(ResCode.UNAUTHORIZED.getCode(), ResCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResBean<T> forbidden(T data) {
        return new ResBean<T>(ResCode.FORBIDDEN.getCode(), ResCode.FORBIDDEN.getMsg(), data);
    }
}
