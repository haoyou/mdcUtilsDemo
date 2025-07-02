package com.example.mdcutilsdemo;

import com.example.mdcutilsdemo.dto.PaymentResponse;
import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testProcessPaymentSuccess() {
        // 准备测试数据
        Order order = new Order();
        order.setOrderId(1001L);
        order.setTotalAmount(new BigDecimal("199.99"));

        // 模拟成功响应
        PaymentResponse successResponse = PaymentResponse.success("PAY_123456", new BigDecimal("199.99"), "CNY");
        when(restTemplate.postForObject(anyString(), any(), eq(PaymentResponse.class)))
                .thenReturn(successResponse);

        // 执行测试
        boolean result = paymentService.processPayment(order);

        // 验证结果
        assertTrue(result);
        verify(restTemplate).postForObject(anyString(), any(), eq(PaymentResponse.class));
    }

    @Test
    void testProcessPaymentFailure() {
        // 准备测试数据
        Order order = new Order();
        order.setOrderId(1002L);
        order.setTotalAmount(new BigDecimal("299.99"));

        // 模拟失败响应
        PaymentResponse failureResponse = PaymentResponse.failure("INSUFFICIENT_BALANCE", "余额不足");
        when(restTemplate.postForObject(anyString(), any(), eq(PaymentResponse.class)))
                .thenReturn(failureResponse);

        // 执行测试
        boolean result = paymentService.processPayment(order);

        // 验证结果
        assertFalse(result);
        verify(restTemplate).postForObject(anyString(), any(), eq(PaymentResponse.class));
    }

    @Test
    void testProcessPaymentException() {
        // 准备测试数据
        Order order = new Order();
        order.setOrderId(1003L);
        order.setTotalAmount(new BigDecimal("399.99"));

        // 模拟异常
        when(restTemplate.postForObject(anyString(), any(), eq(PaymentResponse.class)))
                .thenThrow(new RuntimeException("网络超时"));

        // 执行测试
        boolean result = paymentService.processPayment(order);

        // 验证结果
        assertFalse(result);
    }
}
