package com.example.mdcutilsdemo.service.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.service.PaymentService;
import com.example.mdcutilsdemo.service.mq.producer.OrderMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = OrderMQProducer.ORDER_TOPIC,
        consumerGroup = "payment-service-group",
        selectorExpression = OrderMQProducer.PAYMENT_TAG
)
@Slf4j
public class OrderPaymentConsumer implements RocketMQListener<String> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onMessage(String message) {
        log.info("收到支付处理消息: {}", message);
        Order order = JSON.parseObject(message, Order.class);
        log.info("开始处理支付: 订单ID={}", order.getOrderId());

        // 调用支付服务
        boolean success = paymentService.processPayment(order);

        if(success) {
            log.info("支付成功: 订单ID={}", order.getOrderId());
        } else {
            log.warn("支付失败: 订单ID={}", order.getOrderId());
        }
    }
}
