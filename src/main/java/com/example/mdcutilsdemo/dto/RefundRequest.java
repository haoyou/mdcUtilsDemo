package com.example.mdcutilsdemo.dto;

import java.math.BigDecimal;

public class RefundRequest {
    private Long orderId;           // 订单ID
    private String paymentId;       // 支付平台交易ID
    private BigDecimal amount;      // 退款金额
    private String currency;        // 退款币种
    private String reason;          // 退款原因
    private String notifyUrl;       // 退款结果通知URL

    // 构造函数
    public RefundRequest() {
    }

    public RefundRequest(Long orderId, BigDecimal amount) {
        this(orderId, null, amount, "CNY", "用户申请退款", null);
    }

    public RefundRequest(Long orderId, String paymentId, BigDecimal amount,
                         String currency, String reason, String notifyUrl) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.reason = reason;
        this.notifyUrl = notifyUrl;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String toString() {
        return "RefundRequest{" +
                "orderId=" + orderId +
                ", paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
