package com.br.refera.sysorder.service;

import com.br.refera.sysorder.entity.Order;
import com.br.refera.sysorder.model.OrderModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    List<Order> getListOrder() throws ApiException;
    Order getOrderById(Long id) throws ApiException;
    Order createOrder(OrderModel orderModel) throws ApiException;
    Order updateOrder(OrderModel orderModel) throws ApiException;
    void deleteOrder(Order order) throws ApiException;
}
