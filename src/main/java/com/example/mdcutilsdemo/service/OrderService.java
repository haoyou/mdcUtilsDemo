package com.example.mdcutilsdemo.service;// 文件路径: src/main/java/com/example/mdcutilsdemo/service/order/OrderService.java

import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.entity.OrderItem;

import java.util.List;

public interface OrderService {
    /**
     * 创建订单
     * @param userId 用户ID
     * @param items 订单项列表
     * @return 创建的订单对象
     */
    Order createOrder(Long userId, List<OrderItem> items);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 新状态
     */
    void updateOrderStatus(Long orderId, String status);

    /**
     * 根据订单ID获取订单详情
     * @param orderId 订单ID
     * @return 订单对象
     */
    Order getOrderById(Long orderId);
}
