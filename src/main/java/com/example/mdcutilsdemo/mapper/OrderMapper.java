package com.example.mdcutilsdemo.mapper;

import com.example.mdcutilsdemo.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    // 插入订单
    int insertOrder(Order order);

    // 根据订单ID查询订单
    Order selectByOrderId(@Param("orderId") Long orderId);

    // 更新订单状态
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);

    // 查询用户订单列表
    List<Order> selectOrdersByUserId(@Param("userId") Long userId);
}
