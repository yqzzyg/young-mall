package com.young.mall.notify;

import lombok.Data;

/**
 * @Description: 发送短信的返回结果
 * @Author: yqz
 * @CreateDate: 2020/11/2 17:02
 */
@Data
public class SmsResult {
    private boolean successful;
    private Object result;

}
