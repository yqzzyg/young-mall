package com.young.mall.notify;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.young.mall.config.notify.NotifyProperties;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/4 17:46
 */
@Data
public class SmsSenderAliyunImpl implements SmsSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private IAcsClient iAcsClient;

    private NotifyProperties properties;


    @Override
    public SmsResult send(String phone, String content) {

        return null;
    }

    @Override
    public SmsResult sendWithTemplate(String phone, String templateId, Map<String, Object> map) {
        Object params = map.get("params");
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "mall商城");
        request.putQueryParameter("TemplateCode", templateId);
        request.putQueryParameter("TemplateParam", params.toString());

        CommonResponse commonResponse = null;
        try {
            commonResponse = iAcsClient.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        SmsResult smsResult = new SmsResult();
        smsResult.setSuccessful(true);
        smsResult.setResult(commonResponse.getData());
        return smsResult;
    }
}
