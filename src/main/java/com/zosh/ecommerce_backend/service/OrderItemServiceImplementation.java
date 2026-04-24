package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.model.OrderItem;
import com.zosh.ecommerce_backend.repository.OrderItemRepository;

public class OrderItemServiceImplementation implements OrderItemService {

    private OrderItemRepository orderItemRepository;


    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
