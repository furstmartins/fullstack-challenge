package com.br.refera.sysorder.controller;

import com.br.refera.sysorder.entity.Order;
import com.br.refera.sysorder.model.OrderModel;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listOrders() throws ApiException {
        return ResponseEntity.ok(orderService.getListOrder());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws ApiException {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody OrderModel orderModel) throws ApiException {
        return new ResponseEntity<>(orderService.createOrder(orderModel), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderModel orderModel) throws ApiException {
        orderModel.setId(id);
        return new ResponseEntity<>(orderService.updateOrder(orderModel), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) throws ApiException {
        orderService.deleteOrder(Order.builder().id(id).build());
        return ResponseEntity.noContent().build();
    }
}
