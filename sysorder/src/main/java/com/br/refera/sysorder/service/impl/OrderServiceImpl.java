package com.br.refera.sysorder.service.impl;

import com.br.refera.sysorder.entity.Category;
import com.br.refera.sysorder.entity.Order;
import com.br.refera.sysorder.model.OrderModel;
import com.br.refera.sysorder.repository.CategoryRepository;
import com.br.refera.sysorder.repository.OrderRepository;
import com.br.refera.sysorder.service.ApiException;
import com.br.refera.sysorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Order> getListOrder() throws ApiException {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw ApiException.notFound("Register not found with this parameters", "Register not found");
        }
        return orderList;
    }

    @Override
    public Order getOrderById(Long id) throws ApiException {
        return orderRepository.findById(id).orElseThrow(() ->
                ApiException.notFound("Register not found with this parameters", "Register not found"));
    }

    @Override
    public Order createOrder(OrderModel orderModel) throws ApiException {
        if (orderModel.getId() != null && orderRepository.findById(orderModel.getId()).isPresent()) {
            throw ApiException.badRequest("Order already exist with this id", "Bad parameter");
        }
        Order order = createOrderByOrderModel(orderModel);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderModel orderModel) throws ApiException {
        if (orderRepository.findById(orderModel.getId()).isEmpty()) {
            throw ApiException.notFound("Order informed not found", "Order not found");
        }
        Order order = createOrderByOrderModel(orderModel);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) throws ApiException {
        if (orderRepository.findById(order.getId()).isEmpty()) {
            throw ApiException.notFound("Order informed not found", "Order not found");
        }
        orderRepository.delete(order);
    }

    private Order createOrderByOrderModel(OrderModel orderModel) throws ApiException {
        Order order = new Order();
        BeanUtils.copyProperties(orderModel, order);
        if (orderModel.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(orderModel.getCategoryId());
            if (category.isPresent()) {
                order.setCategory(category.get());
            } else {
                throw ApiException.badRequest("Parameter categoryId is wrong", "Parameter error");
            }
        }
        return order;
    }
}
