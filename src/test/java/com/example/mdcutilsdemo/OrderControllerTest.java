package com.example.mdcutilsdemo;

import com.example.mdcutilsdemo.controller.OrderController;
import com.example.mdcutilsdemo.entity.Order;
import com.example.mdcutilsdemo.service.OrderService;
import com.example.mdcutilsdemo.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;


    @Test
    void createOrder_Success() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);

        when(orderService.createOrder(anyLong(), any())).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1001}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    void getOrderById_Found() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);

        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(null);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        mockMvc.perform(put("/orders/1/status?status=PAID"))
                .andExpect(status().isOk());

        Mockito.verify(orderService).updateOrderStatus(1L, "PAID");
    }

    @Test
    void handlePaymentCallback_Success() throws Exception {
        when(paymentService.handlePaymentCallback(any(), any())).thenReturn(true);

        mockMvc.perform(post("/orders/payment/callback")
                        .param("paymentId", "PAY123")
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(content().string("回调处理成功"));
    }
}
