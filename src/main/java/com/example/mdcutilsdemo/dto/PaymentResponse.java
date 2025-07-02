package com.example.mdcutilsdemo.dto;

import java.math.BigDecimal;

/**
 * 支付响应对象
 * 用于封装支付网关返回的响应数据
 */
public class PaymentResponse {
    private String code;          // 响应码 (SUCCESS/FAIL)
    private String paymentId;     // 支付平台交易ID
    private BigDecimal amount;    // 实际支付金额
    private String currency;      // 支付币种
    private String status;        // 支付状态 (PAID/UNPAID)
    private String errorCode;     // 错误码
    private String errorMsg;      // 错误信息
    private long timestamp;       // 响应时间戳

    // 无参构造函数
    public PaymentResponse() {
    }

    // 全参构造函数
    public PaymentResponse(String code, String paymentId, BigDecimal amount,
                           String currency, String status, String errorCode,
                           String errorMsg, long timestamp) {
        this.code = code;
        this.paymentId = paymentId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = timestamp;
    }

    // 成功响应的快捷创建方法
    public static PaymentResponse success(String paymentId, BigDecimal amount, String currency) {
        return new PaymentResponse(
                "SUCCESS",
                paymentId,
                amount,
                currency,
                "PAID",
                null,
                null,
                System.currentTimeMillis()
        );
    }

    // 失败响应的快捷创建方法
    public static PaymentResponse failure(String errorCode, String errorMsg) {
        return new PaymentResponse(
                "FAIL",
                null,
                null,
                null,
                "UNPAID",
                errorCode,
                errorMsg,
                System.currentTimeMillis()
        );
    }

    // 检查是否成功的便捷方法
    public boolean isSuccess() {
        return "SUCCESS".equals(code);
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "code='" + code + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
