package com.young.mall.notify;

import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/2 17:03
 */
public interface SmsSender {
    /**
     * 发送短信息
     *
     * @param phone   接收通知的电话号码
     * @param content 短消息内容
     * @return
     */
    SmsResult send(String phone, String content);

    /**
     * 通过短信模版发送短信息
     *
     * @param phone      接收通知的电话号码
     * @param templateId 通知模板ID
     * @param map     通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     * @return
     */
    SmsResult sendWithTemplate(String phone, String templateId, Map<String,Object> map);

}
