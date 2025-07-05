package com.example.mdcutilsdemo.service.impl;

import com.example.mdcutilsdemo.dto.*;
import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.mapper.OrderMapper;
import com.example.mdcutilsdemo.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${payment.api.key}")
    private String encryptedApiKey;

    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate;

    // 从配置文件中获取支付网关地址
    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    @Autowired
    public PaymentServiceImpl(OrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean processPayment(Order order) {
        log.info("开始处理支付: 订单ID={}, 金额={}", order.getOrderId(), order.getTotalAmount());

        try {

            // 使用构建器创建请求
//            PaymentRequest request = new PaymentRequest.Builder()
//                    .orderId(order.getOrderId())
//                    .orderNo(order.getOrderNo())
//                    .amount(order.getTotalAmount())
//                    .currency("CNY")
//                    .description("订单支付 - " + order.getOrderNo())
//                    .paymentMethod("WECHAT")
//                    .clientIp("192.168.1.100")
//                    .notifyUrl("https://api.your-domain.com/payment/callback")
//                    .returnUrl("https://www.your-domain.com/order/" + order.getOrderId())
//                    .extraParams("{\"userId\":" + order.getUserId() + "}")
//                    .build();
            // 1. 准备支付请求
//            PaymentRequest request = new PaymentRequest(
//                    order.getOrderId(),
//                    order.getOrderNo(), // 添加订单编号
//                    order.getTotalAmount(),
//                    "CNY",
//                    "订单支付 - " + order.getOrderNo(),
//                    "ALIPAY", // 默认支付宝支付
//                    "192.168.1.100", // 实际项目中应从请求中获取
//                    "https://your-domain.com/payment/callback", // 支付回调URL
//                    "https://your-domain.com/order/" + order.getOrderId(), // 支付完成返回URL
//                    "{\"userId\": " + order.getUserId() + "}" // 额外参数
//            );
//
//            log.debug("支付请求参数: {}", request);
//
//            // 2. 设置请求头
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("X-API-KEY", encryptedApiKey);
//            headers.set("X-Request-Id", UUID.randomUUID().toString()); // 请求ID用于追踪
//
//            HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);
//
//            // 3. 发送支付请求
//            PaymentResponse response = restTemplate.postForObject(
//                    paymentGatewayUrl + "/payments",
//                    entity,
//                    PaymentResponse.class
//            );


            PaymentResponse response = PaymentResponse.success("1234567890", order.getTotalAmount(), "CNY");

            // 2. 处理支付结果
            if (response != null && "SUCCESS".equals(response.getCode())) {
                log.info("支付成功: 订单ID={}, 支付ID={}", order.getOrderId(), response.getPaymentId());

                // 3. 更新订单状态
                orderMapper.updateOrderStatus(order.getOrderId(), "PAID");
                return true;
            } else {
                log.warn("支付失败: 订单ID={}, 错误码={}", order.getOrderId(),
                        response != null ? response.getCode() : "无响应");
                return false;
            }
        } catch (Exception e) {
            log.error("支付处理异常: 订单ID=" + order.getOrderId(), e);
            return false;
        }
    }

    @Override
    public String checkPaymentStatus(Long orderId) {
        log.info("查询支付状态: 订单ID={}", orderId);

        try {
            PaymentStatusResponse response = restTemplate.getForObject(
                    paymentGatewayUrl + "/status?orderId=" + orderId,
                    PaymentStatusResponse.class
            );

            return response != null ? response.getStatus() : "UNKNOWN";
        } catch (Exception e) {
            log.error("支付状态查询异常", e);
            return "ERROR";
        }
    }

    @Override
    public boolean handlePaymentCallback(String paymentId, String status) {
        log.info("处理支付回调: paymentId={}, status={}", paymentId, status);

        // 实际项目中应根据paymentId找到对应订单
        // 这里简化为直接返回true
        return true;
    }

    @Override
    public boolean refund(Long orderId, BigDecimal amount) {
        log.info("处理退款: 订单ID={}, 金额={}", orderId, amount);

        try {
            RefundRequest request = new RefundRequest(orderId, amount);
            RefundResponse response = restTemplate.postForObject(
                    paymentGatewayUrl + "/refund",
                    request,
                    RefundResponse.class
            );

            return response != null && "SUCCESS".equals(response.getCode());
        } catch (Exception e) {
            log.error("退款处理异常", e);
            return false;
        }
    }


}
