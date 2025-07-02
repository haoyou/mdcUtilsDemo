package com.example.mdcutilsdemo.service;

import com.example.mdcutilsdemo.entity.Order;

import java.math.BigDecimal;

/**
 * 支付服务接口
 */
public interface PaymentService {

    /**
     * 处理订单支付
     * @param order 订单对象
     * @return 支付结果 (true: 成功, false: 失败)
     */
    boolean processPayment(Order order);

    /**
     * 查询支付状态
     * @param orderId 订单ID
     * @return 支付状态 (PAID: 已支付, UNPAID: 未支付, FAILED: 支付失败)
     */
    String checkPaymentStatus(Long orderId);

    /**
     * 处理支付回调
     * @param paymentId 支付平台交易ID
     * @param status 支付状态
     * @return 处理结果
     */
    boolean handlePaymentCallback(String paymentId, String status);

    /**
     * 发起退款
     * @param orderId 订单ID
     * @param amount 退款金额
     * @return 退款结果
     */
    boolean refund(Long orderId, BigDecimal amount);
}
