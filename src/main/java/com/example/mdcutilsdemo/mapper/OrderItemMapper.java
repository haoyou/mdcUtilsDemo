package com.example.mdcutilsdemo.mapper;

import com.example.mdcutilsdemo.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    // 批量插入订单项
    int batchInsert(@Param("items") List<OrderItem> items);

    // 根据订单ID查询订单项
    List<OrderItem> selectItemsByOrderId(@Param("orderId") Long orderId);
}
