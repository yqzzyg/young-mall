package com.young.mall.rabbitMQ;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungUserLog;
import com.young.mall.domain.MallLog;
import com.young.mall.service.MallUserLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 记录日志消息的消费者
 * @Author: yqz
 * @CreateDate: 2021/1/22 12:22
 */
@Component
@RabbitListener(queues = "mall.log.insert")
public class LogMsgReceiver {

    private static Logger LOGGER = LoggerFactory.getLogger(LogMsgReceiver.class);

    @Autowired
    private MallUserLogService userLogService;

    @RabbitHandler
    public void handle(String mallLog) {

        LOGGER.info("process mallLog:{}", mallLog);

        YoungUserLog userLog = JSONUtil.toBean(mallLog,YoungUserLog.class);

        userLogService.insertLog(userLog);
    }
}
