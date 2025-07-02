package com.example.mdcutilsdemo.dto;

import java.math.BigDecimal;

public class PaymentStatusResponse {
    private String status;          // 支付状态 (PAID, UNPAID, REFUNDED, FAILED)
    private String paymentId;       // 支付平台交易ID
    private BigDecimal amount;      // 支付金额
    private String currency;        // 支付币种
    private long paymentTime;       // 支付时间戳
    private String errorCode;       // 错误码
    private String errorMsg;        // 错误信息

    // 构造函数
    public PaymentStatusResponse() {
    }

    public PaymentStatusResponse(String status, String paymentId, BigDecimal amount,
                                 String currency, long paymentTime, String errorCode,
                                 String errorMsg) {
        this.status = status;
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.paymentTime = paymentTime;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "PaymentStatusResponse{" +
                "status='" + status + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", paymentTime=" + paymentTime +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
