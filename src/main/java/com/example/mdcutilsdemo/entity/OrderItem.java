package com.example.mdcutilsdemo.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class OrderItem {
    private Long itemId;        // 订单项ID
    private Long orderId;       // 所属订单ID
    private Long productId;     // 商品ID
    private String productName; // 商品名称
    private Integer quantity;   // 购买数量
    private BigDecimal price;   // 商品单价
    // 可选字段
    private String skuCode; // SKU编码
    private String category; // 商品分类


    public OrderItem(@NotNull(message = "商品ID不能为空") Long productId, @NotBlank(message = "商品名称不能为空") @Size(max = 100, message = "商品名称长度不能超过100个字符") String productName, @NotNull(message = "商品数量不能为空") @Min(value = 1, message = "商品数量必须大于0") Integer quantity, @NotNull(message = "商品价格不能为空") @DecimalMin(value = "0.01", message = "商品价格必须大于0") BigDecimal price, String skuCode, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.skuCode = skuCode;
        this.category = category;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
