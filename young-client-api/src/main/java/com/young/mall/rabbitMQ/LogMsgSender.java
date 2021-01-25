package com.young.mall.rabbitMQ;

import cn.hutool.json.JSONUtil;
import com.young.mall.domain.MallLog;
import com.young.mall.domain.enums.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 接口访问日志的发送者
 * @Author: yqz
 * @CreateDate: 2021/1/22 12:09
 */
@Component
public class LogMsgSender {

    private static Logger LOGGER = LoggerFactory.getLogger(LogMsgSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 给日志交换机发送消息
     *
     * @param mallLog
     */
    public void sendLogMessage(MallLog mallLog) {

        amqpTemplate.convertAndSend(QueueEnum.QUEUE_LOGGER.getExchange(),
                QueueEnum.QUEUE_LOGGER.getRouteKey(), JSONUtil.toJsonStr(mallLog));

    }

}
