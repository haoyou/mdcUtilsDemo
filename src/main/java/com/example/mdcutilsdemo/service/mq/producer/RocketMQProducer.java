package com.example.mdcutilsdemo.service.mq.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducer {

    private final RocketMQTemplate rocketMQTemplate;

    @Autowired
    public RocketMQProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 发送普通消息
     * @param topic 主题
     * @param message 消息内容
     */
    public void sendMessage(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    /**
     * 发送带Tag的消息
     * @param topic 主题
     * @param tag 标签
     * @param message 消息内容
     */
    public void sendMessageWithTag(String topic, String tag, String message) {
        rocketMQTemplate.convertAndSend(topic + ":" + tag, message);
    }

    /**
     * 发送事务消息
     * @param topic 主题
     * @param message 消息内容
     * @param arg 事务参数
     */
    public void sendTransactionMessage(String topic, String message, Object arg) {
        rocketMQTemplate.sendMessageInTransaction(topic,
                MessageBuilder.withPayload(message).build(), arg);
    }
}
