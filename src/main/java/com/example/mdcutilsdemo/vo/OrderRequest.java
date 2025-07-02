package com.example.mdcutilsdemo.vo;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单创建请求对象
 */
@Data
public class OrderRequest {
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID必须大于0")
    private Long userId;

    @NotBlank(message = "订单来源不能为空")
    private String source = "WEB"; // 默认值: WEB

    @Size(min = 1, message = "订单项不能为空")
    private List<OrderItem> items;

    // 订单项内部类
    @Data
    public static class OrderItem {
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
    }

    // 可选字段
    private String remark; // 订单备注
    private String couponCode; // 优惠券代码
    private String deliveryAddress; // 配送地址
    private String contactPhone; // 联系电话
}
