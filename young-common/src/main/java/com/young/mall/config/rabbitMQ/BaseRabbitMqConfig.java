package com.young.mall.config.rabbitMQ;

import com.young.mall.domain.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: rabbitMQ配置类
 * @Author: yqz
 * @CreateDate: 2021/1/22 11:25
 */
@Slf4j
@Configuration
public class BaseRabbitMqConfig {

/*    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");
            } else {
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;
    }*/

    /*---------------交换机--------------*/

    /**
     * 记录接口访问记录的交换机
     */
    @Bean
    public DirectExchange logDirect() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_LOGGER.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单消息实际消费队列所绑定的交换机
     */
    @Bean
    public DirectExchange orderDirect() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列所绑定的交换机
     */
    @Bean
    public DirectExchange orderTtlDirect() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /*----------------队列-------------*/

    /**
     * log日志队列
     */
    @Bean
    public Queue logQueue() {
        return new Queue(QueueEnum.QUEUE_LOGGER.getName());
    }

    /**
     * 订单实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }

    /**
     * 订单延迟队列（死信队列）
     */
    @Bean
    public Queue orderTtlQueue() {
        /**
         * x-dead-letter-exchange：出现dead letter之后将dead letter重新发送到指定exchange
         * x-dead-letter-routing-key：出现dead letter之后将dead letter重新按照指定的routing-key发送
         *
         * 队列出现dead letter的情况有：
         *
         * 消息或者队列的TTL过期
         * 队列达到最大长度
         * 消息被消费端拒绝（basic.reject or basic.nack）并且requeue=false
         */
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                //到期后转发的交换机
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                //到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())
                .build();
    }


    /*----------------队列与交换机绑定-------------*/

    /**
     * 将log日志队列绑定到交换机
     */
    @Bean
    Binding logBinding(DirectExchange logDirect, Queue logQueue) {
        return BindingBuilder
                .bind(logQueue)
                .to(logDirect)
                .with(QueueEnum.QUEUE_LOGGER.getRouteKey());
    }

    /**
     * 将订单队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect, Queue orderQueue) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect, Queue orderTtlQueue) {
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }
}
