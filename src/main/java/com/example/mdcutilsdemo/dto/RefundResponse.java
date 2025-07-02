package com.example.mdcutilsdemo.dto;

import java.math.BigDecimal;

public class RefundResponse {
    private String code;            // 响应码 (SUCCESS/FAIL)
    private String refundId;        // 退款ID
    private BigDecimal amount;      // 实际退款金额
    private String currency;        // 退款币种
    private String status;          // 退款状态 (REFUNDED, PROCESSING, FAILED)
    private String errorCode;       // 错误码
    private String errorMsg;        // 错误信息
    private long timestamp;         // 响应时间戳

    // 构造函数
    public RefundResponse() {
    }

    public RefundResponse(String code, String refundId, BigDecimal amount,
                          String currency, String status, String errorCode,
                          String errorMsg, long timestamp) {
        this.code = code;
        this.refundId = refundId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = timestamp;
    }

    // 成功响应的快捷创建方法
    public static RefundResponse success(String refundId, BigDecimal amount, String currency) {
        return new RefundResponse(
                "SUCCESS",
                refundId,
                amount,
                currency,
                "REFUNDED",
                null,
                null,
                System.currentTimeMillis()
        );
    }

    // 失败响应的快捷创建方法
    public static RefundResponse failure(String errorCode, String errorMsg) {
        return new RefundResponse(
                "FAIL",
                null,
                null,
                null,
                "FAILED",
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

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
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
        return "RefundResponse{" +
                "code='" + code + '\'' +
                ", refundId='" + refundId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
