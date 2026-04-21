package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.OrderException;
import com.zosh.ecommerce_backend.model.Order;

import java.util.List;

public interface OrderService {

    Order saveOrder(Order order);

    Order findOrderById(Long orderId) throws OrderException;

    List<Order> getAllOrders();

    List<Order> getUsersOrders(Long userId);

    Order placeOrder(Long orderId) throws OrderException;

    Order confirmOrder(Long orderId) throws OrderException;

    Order shipOrder(Long orderId) throws OrderException;

    Order deliverOrder(Long orderId) throws OrderException;

    Order cancelOrder(Long orderId) throws OrderException;

    void deleteOrder(Long orderId) throws OrderException;
}