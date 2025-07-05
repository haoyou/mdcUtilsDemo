package com.example.mdcutilsdemo.service.mq.producer;

import com.alibaba.fastjson.JSON;
import com.example.mdcutilsdemo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMQProducer {
    private final RocketMQTemplate rocketMQTemplate;

    // 定义消息主题常量
    /**
     * ORDER_TOPIC：订单相关的消息主题。
     * PAYMENT_TAG：支付业务的标签。
     * INVENTORY_TAG：库存业务的标签。
     * NOTIFICATION_TAG：通知业务的标签。
     * */
    public static final String ORDER_TOPIC = "ORDER_TOPIC";
    public static final String PAYMENT_TAG = "payment";
    public static final String INVENTORY_TAG = "inventory";
    public static final String NOTIFICATION_TAG = "notification";

    @Autowired
    public OrderMQProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 发送订单创建消息
     * @param order 订单对象
     */
    public void sendOrderCreatedMessage(Order order) {
        // 使用JSON序列化订单对象
        String message = JSON.toJSONString(order);

        // 发送到不同标签，触发不同业务处理
        sendTaggedMessage(PAYMENT_TAG, message);
        sendTaggedMessage(INVENTORY_TAG, message);
        sendTaggedMessage(NOTIFICATION_TAG, message);

        log.info("订单消息已发送: {}", order.getOrderId());
    }

    private void sendTaggedMessage(String tag, String message) {
        String destination = String.format("%s:%s", ORDER_TOPIC, tag);
        rocketMQTemplate.convertAndSend(destination, message);
    }
}
