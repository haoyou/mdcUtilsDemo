package com.example.mdcutilsdemo.controller;

import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.entity.OrderItem;
import com.example.mdcutilsdemo.service.OrderService;
import com.example.mdcutilsdemo.service.PaymentService;
import com.example.mdcutilsdemo.vo.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private  OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 创建订单
     * @param request 订单请求对象
     * @return 创建的订单对象
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        log.info("收到创建订单请求: 用户ID={}, 来源={}", request.getUserId(), request.getSource());

        try {
            // 将DTO转换为实体
            List<OrderItem> items = Optional.ofNullable(request.getItems())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(dtoItem -> {
                        // 验证关键字段合法性（根据业务需求调整）
                        if (dtoItem.getProductId() == null) {
                            throw new IllegalArgumentException("Product ID cannot be null");
                        }
                        if (dtoItem.getQuantity() <= 0) {
                            throw new IllegalArgumentException("Quantity must be positive");
                        }
                        if (dtoItem.getPrice().signum() <= 0) { // 假设 Price 是 BigDecimal
                            throw new IllegalArgumentException("Price must be positive");
                        }

                        return new OrderItem(
                                dtoItem.getProductId(),
                                dtoItem.getProductName(),
                                dtoItem.getQuantity(),
                                dtoItem.getPrice(),
                                dtoItem.getSkuCode(),
                                dtoItem.getCategory()
                        );
                    }).collect(Collectors.toList());;


            Order order = orderService.createOrder(
                    request.getUserId(),
                    items
            );

            log.info("订单创建成功: 订单ID={}", order.getOrderId());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("订单创建失败: 用户ID={}", request.getUserId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 根据订单ID查询订单
     * @param orderId 订单ID
     * @return 订单对象
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        log.info("查询订单: 订单ID={}", orderId);

        try {
            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                log.info("订单查询成功: 订单ID={}", orderId);
                return ResponseEntity.ok(order);
            } else {
                log.warn("订单不存在: 订单ID={}", orderId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("订单查询异常: 订单ID={}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 新状态
     * @return 操作结果
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        log.info("更新订单状态: 订单ID={}, 状态={}", orderId, status);

        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("订单状态更新失败: 订单ID={}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 处理支付回调
     * @param paymentId 支付ID
     * @param status 支付状态
     * @return 处理结果
     */
    @PostMapping("/payment/callback")
    public ResponseEntity<String> handlePaymentCallback(
            @RequestParam String paymentId,
            @RequestParam String status) {
        log.info("处理支付回调: paymentId={}, status={}", paymentId, status);

        try {
            boolean result = paymentService.handlePaymentCallback(paymentId, status);
            if (result) {
                return ResponseEntity.ok("回调处理成功");
            } else {
                return ResponseEntity.badRequest().body("回调处理失败");
            }
        } catch (Exception e) {
            log.error("支付回调处理异常", e);
            return ResponseEntity.internalServerError().body("服务器内部错误");
        }
    }




}
