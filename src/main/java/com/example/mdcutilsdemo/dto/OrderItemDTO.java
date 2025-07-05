package com.example.mdcutilsdemo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
@Data
@Getter
@Setter
public class OrderItemDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String productName;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量必须大于0")
    private Integer quantity;

    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    // 可选字段
    private String skuCode; // SKU编码
    private String category; // 商品分类

    // 添加无参构造方法
    public OrderItemDTO() {}

    public OrderItemDTO(Long productId, String productName, Integer quantity, BigDecimal price, String skuCode, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.skuCode = skuCode;
        this.category = category;
    }

}

