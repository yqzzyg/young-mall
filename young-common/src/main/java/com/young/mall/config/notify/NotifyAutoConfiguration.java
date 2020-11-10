package com.young.mall.config.notify;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.github.qcloudsms.SmsSingleSender;
import com.young.mall.notify.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/3 11:25
 */
@Configuration
@EnableConfigurationProperties(NotifyProperties.class)
public class NotifyAutoConfiguration {

    private final NotifyProperties properties;

    public NotifyAutoConfiguration(NotifyProperties properties) {
        this.properties = properties;
    }

    @Bean
    public NotifyService notifyService() {
        NotifyService notifyService = new NotifyService();
        NotifyProperties.Mail mail = properties.getMail();

        if (mail.isEnable()) {

            /*notifyService.setMailSender(mailSender());
            notifyService.setSendFrom(mail.getSendfrom());
            notifyService.setSendTo(mail.getSendto());*/

            notifyService.setSslMailSender(sslMailSender());
        }

        NotifyProperties.Sms sms = properties.getSms();
        if (sms.isEnable()) {
            notifyService.setSmsSender(smsSenderTencentImpl());
            notifyService.setSmsTemplate(sms.getTemplate());
        }

        NotifyProperties.AliyunSms aliyunSms = properties.getAliyunSms();
        if (aliyunSms.isEnable()) {
            notifyService.setSmsSender(smsSenderAliyun());
            notifyService.setSmsTemplate(aliyunSms.getTemplate());
        }

        NotifyProperties.Wx wx = properties.getWx();
        if (wx.isEnable()) {
            notifyService.setWxTemplateSender(wxTemplateSender());
            notifyService.setWxTemplate(wx.getTemplate());

        }
        return notifyService;

    }

    @Bean
    public SslMailSender sslMailSender() {
        NotifyProperties.Mail mail = properties.getMail();
        SslMailSender sslMailSender = new SslMailSender();

        sslMailSender.setHost(mail.getHost());
        sslMailSender.setUserName(mail.getUsername());
        sslMailSender.setPassword(mail.getPassword());
        sslMailSender.setFromAddress(mail.getSendfrom());
        sslMailSender.setToAddress(mail.getSendto());
        sslMailSender.setFromName(mail.getSendfrom());
        // 默认465端口，25端口云服务器基本难以申请
        sslMailSender.setPort("465");
        sslMailSender.setSslEnabled("true");
        return sslMailSender;
    }

    @Bean
    public SmsSenderTencentImpl smsSenderTencentImpl() {
        NotifyProperties.Sms sms = properties.getSms();

        SmsSenderTencentImpl senderTencent = new SmsSenderTencentImpl();
        senderTencent.setSender(new SmsSingleSender(sms.getAppid(), sms.getAppkey()));
        return senderTencent;
    }

    @Bean
    public SmsSenderAliyunImpl smsSenderAliyun() {
        NotifyProperties.AliyunSms aliyunSms = properties.getAliyunSms();

        SmsSenderAliyunImpl senderAliyun = new SmsSenderAliyunImpl();

        DefaultProfile profile = DefaultProfile.getProfile(aliyunSms.getRegionId(), aliyunSms.getAccessKeyId(), aliyunSms.getAccessSecret());

        senderAliyun.setIAcsClient(new DefaultAcsClient(profile));
        return senderAliyun;
    }


    @Bean
    public WxTemplateSender wxTemplateSender() {
        WxTemplateSender wxTemplateSender = new WxTemplateSender();
        return wxTemplateSender;
    }

    @Bean
    public JavaMailSender mailSender() {
        NotifyProperties.Mail mailConfig = properties.getMail();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHost());
        mailSender.setUsername(mailConfig.getUsername());
        mailSender.setPassword(mailConfig.getPassword());
        return mailSender;
    }
}
