package com.example.mdcutilsdemo.dto;

import java.math.BigDecimal;

// 在 PaymentServiceImpl 类中添加 PaymentRequest 内部类
public  class PaymentRequest {
    private Long orderId;          // 订单ID
    private String orderNo;        // 订单编号（可选）
    private BigDecimal amount;     // 支付金额
    private String currency;       // 支付币种
    private String description;    // 支付描述
    private String paymentMethod;  // 支付方式（如：ALIPAY, WECHAT, BANK_CARD）
    private String clientIp;       // 客户端IP
    private String notifyUrl;      // 支付结果通知URL
    private String returnUrl;      // 支付完成返回URL
    private String extraParams;    // 额外参数（JSON格式）

    // 全参构造函数
    public PaymentRequest(Long orderId, BigDecimal amount, String currency, String description) {
        this(orderId, null, amount, currency, description, "ALIPAY", null, null, null, null);
    }

    // 完整构造函数
    public PaymentRequest(Long orderId, String orderNo, BigDecimal amount, String currency,
                          String description, String paymentMethod, String clientIp,
                          String notifyUrl, String returnUrl, String extraParams) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.clientIp = clientIp;
        this.notifyUrl = notifyUrl;
        this.returnUrl = returnUrl;
        this.extraParams = extraParams;
    }

    // 在 PaymentRequest 类中添加构建器
    public static class Builder {
        private Long orderId;
        private String orderNo;
        private BigDecimal amount;
        private String currency;
        private String description;
        private String paymentMethod = "ALIPAY";
        private String clientIp;
        private String notifyUrl;
        private String returnUrl;
        private String extraParams;

        public Builder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        // 其他字段的构建方法...

        public Builder extraParams(String extraParams) {
            this.extraParams = extraParams;
            return this;
        }

        public PaymentRequest build() {
            return new PaymentRequest(
                    orderId, orderNo, amount, currency, description,
                    paymentMethod, clientIp, notifyUrl, returnUrl, extraParams
            );
        }
    }



    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", extraParams='" + extraParams + '\'' +
                '}';
    }
}
