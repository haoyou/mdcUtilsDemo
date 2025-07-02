package com.example.mdcutilsdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentConfigTest {

    @Autowired
    private Environment env;

    @Test
    void paymentGatewayUrlShouldBeConfigured() {
        String url = env.getProperty("payment.gateway.url");
        assertNotNull(url);
        assertTrue(url.startsWith("http"));
        System.out.println("Payment Gateway URL: " + url);
    }

    @Test
    void paymentApiKeyShouldBeConfigured() {
        String apiKey = env.getProperty("payment.api.key");
        assertNotNull(apiKey);
        // 不要打印真实密钥
    }
}
