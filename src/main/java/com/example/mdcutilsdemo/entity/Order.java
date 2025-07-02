package com.example.mdcutilsdemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private Long orderId;          // 订单ID
    private String orderNo;        // 订单编号
    private Long userId;           // 用户ID
    private BigDecimal totalAmount; // 订单总金额
    private String status;         // 订单状态（CREATED, PAID, COMPLETED, CANCELLED）
    private Date createTime;       // 创建时间
    private Date updateTime;       // 更新时间

    // 订单项列表（非数据库字段，用于业务处理）
    private List<OrderItem> items;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
