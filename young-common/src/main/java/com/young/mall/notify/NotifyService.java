package com.young.mall.notify;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.young.mall.dto.MailDto;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商城通知服务类
 * @Author: yqz
 * @CreateDate: 2020/11/2 16:46
 */
@Data
public class NotifyService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private MailSender mailSender;
    private String sendFrom;
    private String sendTo;

    private SslMailSender sslMailSender;

    private SmsSender smsSender;
    private List<Map<String, String>> smsTemplate = new ArrayList<>();

    private WxTemplateSender wxTemplateSender;
    private List<Map<String, String>> wxTemplate = new ArrayList<>();

    /**
     * 短信消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param message     短消息内容，这里短消息内容必须已经在短信平台审核通过
     */
    @Async
    public void notifySms(String phoneNumber, String message) {
        if (BeanUtil.isEmpty(smsSender)) {
            return;
        }
        SmsResult smsResult = smsSender.send(phoneNumber, message);
        logger.info("异步短信消息通知 结果:{}", JSONUtil.toJsonStr(smsResult));

    }

    /**
     * 短信模版消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param map         通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */
    @Async
    public void notifySmsTemplate(String phoneNumber, NotifyType notifyType, Map<String, Object> map) {
        if (BeanUtil.isEmpty(smsSender)) {
            return;
        }
        String templateId = getTemplateId(notifyType, smsTemplate);
        if (StrUtil.isEmpty(templateId)) {
            return;
        }
        Integer id = Integer.valueOf(templateId);
        SmsResult smsResult = smsSender.sendWithTemplate(phoneNumber, id, map);
        logger.info("短信模版消息通知结果:{}", JSONUtil.toJsonStr(smsResult));
    }

    /**
     * 以同步的方式发送短信模版消息通知
     *
     * @param phoneNumber 接收通知的电话号码
     * @param notifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param map         通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */
    public SmsResult notifySmsTemplateSync(String phoneNumber, NotifyType notifyType, Map<String, Object> map) {
        if (BeanUtil.isEmpty(smsSender)) {
            return null;
        }
        Integer templateId = Integer.valueOf(getTemplateId(notifyType, smsTemplate));
        SmsResult smsResult = smsSender.sendWithTemplate(phoneNumber, templateId, map);
        logger.info("以同步的方式发送短信模版消息通知 结果:{}", JSONUtil.toJsonStr(smsResult));

        return smsResult;
    }

    /**
     * 微信模版消息通知,不跳转
     * 该方法会尝试从数据库获取缓存的FormId去发送消息
     *
     * @param toUser     接收者openId
     * @param notifyType 通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params     通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     */
    @Async
    public void notifyWxTemplate(String toUser, NotifyType notifyType, String[] params) {
        if (BeanUtil.isEmpty(smsSender)) {
            return;
        }
        String templateId = getTemplateId(notifyType, wxTemplate);

        wxTemplateSender.sendWechatMsg(toUser, templateId, params);

    }

    /**
     * 微信模版消息通知，带跳转
     * 该方法会尝试从数据库获取缓存的FormId去发送消息
     *
     * @param toUser     接收者openId
     * @param notifyType 通知类别，通过该枚举值在配置文件中获取相应的模版ID
     * @param params     通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
     * @param page       点击消息跳转的页面
     */
    @Async
    public void notifyWxTemplate(String toUser, NotifyType notifyType, String[] params, String page) {
        if (BeanUtil.isEmpty(smsSender)) {
            return;
        }
        String templateId = getTemplateId(notifyType, wxTemplate);
        wxTemplateSender.sendWechatMsg(toUser, templateId, params, page);
    }

    /**
     * 邮件消息通知, 接收者在spring.mail.sendto中指定
     *
     * @param mailDto 邮件
     */
    @Async
    public void notifyMail(MailDto mailDto) {
        if (BeanUtil.isEmpty(mailSender)) {
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getContent());
        mailSender.send(message);

    }

    /**
     * 发送ssl邮件
     *
     * @param mailDto 邮件
     */
    @Async
    public void notifySslMail(MailDto mailDto) {
        if (BeanUtil.isEmpty(sslMailSender)) {
            return;
        }
        sslMailSender.sendMails(mailDto.getTitle(), mailDto.getContent());
    }

    /**
     * 根据收件人邮箱发送邮件
     * @param mailDto
     */
    @Async
    public void notifySslMailWithTo(MailDto mailDto) {
        if (BeanUtil.isEmpty(sslMailSender)) {
            return;
        }
        sslMailSender.setToAddress(mailDto.getTo());
        boolean status = sslMailSender.sendMails(mailDto.getTitle(), mailDto.getContent());
        logger.info("根据收件人邮箱发送邮件，发送状态：{}",status);
    }

    private String getTemplateId(NotifyType notifyType, List<Map<String, String>> values) {
        for (Map<String, String> item : values) {
            String notifyTypeStr = notifyType.getType();

            if (item.get("name").equals(notifyTypeStr)) {
                return item.get("templateId");
            }
        }
        return null;
    }

}