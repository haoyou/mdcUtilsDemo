package com.example.mdcutilsdemo.controller;

import com.example.mdcutilsdemo.entity.User;
import com.example.mdcutilsdemo.service.mq.producer.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MQTestController {
    // 添加生产者注入
    @Autowired
    private RocketMQProducer rocketMQProducer;

    // 添加测试接口
    @GetMapping("/test/mq/send")
    public String testMqSend(@RequestParam String message) {
        rocketMQProducer.sendMessage("TEST_TOPIC", message);
        return "消息发送成功";
    }

    @PostMapping("/test/mq/user")
    public String testMqUser(@RequestBody User user, @RequestParam String tag) {
        rocketMQProducer.sendMessageWithTag("USER_TOPIC", tag, user.toString());
        return "用户消息发送成功";
    }
}
