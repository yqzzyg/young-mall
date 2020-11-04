package com.young.mall.notify;

import com.aliyuncs.IAcsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/4 17:46
 */
public class SmsSenderAliyunImpl implements SmsSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private IAcsClient iAcsClient;


    @Override
    public SmsResult send(String phone, String content) {

        return null;
    }

    @Override
    public SmsResult sendWithTemplate(String phone, int templateId, String[] params) {
/*        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "OpenApi");
        request.putQueryParameter("TemplateCode", temCode);
        request.putQueryParameter("TemplateParam", content);
        CommonResponse commonResponse = iAcsClient.getCommonResponse(request);*/

        return null;
    }
}
