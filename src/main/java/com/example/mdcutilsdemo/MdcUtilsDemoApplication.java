package com.example.mdcutilsdemo;

import com.example.mdcutilsdemo.service.mq.consumer.RocketMQConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MdcUtilsDemoApplication implements CommandLineRunner {

    @Autowired(required = false)
    private RocketMQConsumer rocketMQConsumer;
    public static void main(String[] args) {
        SpringApplication.run(MdcUtilsDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (rocketMQConsumer != null) {
            log.info("RocketMQ消费者已初始化");
        } else {
            log.error("RocketMQ消费者未初始化!");
        }
    }
}
