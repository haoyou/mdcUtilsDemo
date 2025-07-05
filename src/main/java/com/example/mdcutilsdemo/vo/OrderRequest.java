package com.example.mdcutilsdemo.vo;

import com.example.mdcutilsdemo.dto.OrderItemDTO;
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
    private List<OrderItemDTO> items;

    // 可选字段
    private String remark; // 订单备注
    private String couponCode; // 优惠券代码
    private String deliveryAddress; // 配送地址
    private String contactPhone; // 联系电话
}
