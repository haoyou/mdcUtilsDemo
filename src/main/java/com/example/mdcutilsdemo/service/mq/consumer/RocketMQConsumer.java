package com.example.mdcutilsdemo.service.mq.consumer;

import com.example.mdcutilsdemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RocketMQConsumer {

    /**
     * 普通消息消费者
     */
    @Service
    @RocketMQMessageListener(
            topic = "TEST_TOPIC",
            consumerGroup = "my-consumer-group",
            selectorExpression = "*"  // 消费所有Tag
    )
    public class SimpleConsumer implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            log.info("收到消息: " + message);
            // 处理业务逻辑
        }
    }

    /**
     * 带Tag的消息消费者
     */
    @Service
    @RocketMQMessageListener(
            topic = "USER_TOPIC",
            consumerGroup = "my-group",
            selectorExpression = "create || update"  // 只消费create和update标签
    )
    public class TagConsumer implements RocketMQListener<User> {
        @Override
        public void onMessage(User user) {
            System.out.println("收到用户变更消息: " + user);
            // 处理用户变更逻辑
        }
    }
}