package com.young.mall.notify;

import cn.hutool.json.JSONUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description: 腾讯云短信服务
 * @Author: yqz
 * @CreateDate: 2020/11/3 11:28
 */
public class SmsSenderTencentImpl implements SmsSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private SmsSingleSender sender;

    public SmsSingleSender getSender() {
        return sender;
    }

    public void setSender(SmsSingleSender sender) {
        this.sender = sender;
    }

    @Override
    public SmsResult send(String phone, String content) {

        SmsSingleSenderResult result = null;
        try {
            result = sender.send(0, "86", phone, content, "", "");
            logger.info("发送短信成功：{}", JSONUtil.toJsonStr(result));

            SmsResult smsResult = new SmsResult();
            smsResult.setResult(result);
            smsResult.setSuccessful(true);
            return smsResult;

        } catch (HTTPException | IOException e) {
            logger.info("发送短信失败：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SmsResult sendWithTemplate(String phone, int templateId, String[] params) {
        try {
            SmsSingleSenderResult result = sender.sendWithParam("86", phone, templateId, params, "", "", "");
            logger.info("短信模版消息通知 发送成功：{}", JSONUtil.toJsonStr(result));

            SmsResult smsResult = new SmsResult();
            smsResult.setSuccessful(true);
            smsResult.setResult(result);
            return smsResult;
        } catch (HTTPException | IOException e) {
            logger.info("短信模版消息通知 发送成功：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
