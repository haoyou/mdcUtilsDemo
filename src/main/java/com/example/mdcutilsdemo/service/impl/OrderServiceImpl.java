package com.example.mdcutilsdemo.service.impl;// 文件路径: src/main/java/com/example/mdcutilsdemo/service/order/impl/OrderServiceImpl.java

import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.entity.OrderItem;
import com.example.mdcutilsdemo.mapper.OrderItemMapper;
import com.example.mdcutilsdemo.mapper.OrderMapper;
import com.example.mdcutilsdemo.service.OrderService;
import com.example.mdcutilsdemo.service.mq.producer.OrderMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderMQProducer orderMQProducer;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper, OrderMQProducer orderMQProducer) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.orderMQProducer = orderMQProducer;
    }

    @Override
    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items) {
        // 创建订单对象
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(calculateTotalAmount(items));
        order.setStatus("CREATED");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        // 保存订单到数据库
        orderMapper.insertOrder(order);

        // 设置订单项关联的订单ID
        for (OrderItem item : items) {
            item.setOrderId(order.getOrderId());
        }

         //保存订单项
         orderItemMapper.batchInsert(items);

        // 发送订单创建消息
        orderMQProducer.sendOrderCreatedMessage(order);

        return order;
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        orderMapper.updateOrderStatus(orderId, status);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
