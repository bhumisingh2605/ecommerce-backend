package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.OrderException;
import com.zosh.ecommerce_backend.model.Order;
import com.zosh.ecommerce_backend.model.OrderStatus;
import com.zosh.ecommerce_backend.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ================= SAVE ORDER =================
    @Override
    public Order saveOrder(Order order) {

        // ✅ Default status only if not already set
        if (order.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatus.PLACED);
        }

        // ✅ Created time
        order.setCreatedAt(LocalDateTime.now());

        // ✅ Estimated Delivery Date (ONLY if not set)
        if (order.getDeliveryDate() == null) {
            int deliveryDays = 3 + new Random().nextInt(3); // 3–5 days
            order.setDeliveryDate(LocalDateTime.now().plusDays(deliveryDays));
        }

        return orderRepository.save(order);
    }

    // ================= FIND BY ID =================
    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found: " + orderId));
    }

    // ================= USER ORDERS =================
    @Override
    public List<Order> getUsersOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // ================= ALL ORDERS =================
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ================= PLACE ORDER =================
    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.CREATED) {
            return order; // already placed
        }

        order.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    // ================= CONFIRM =================
    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Order must be PLACED to confirm");
        }

        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    // ================= SHIP =================
    @Override
    public Order shipOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Order must be CONFIRMED to ship");
        }

        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    // ================= OUT FOR DELIVERY =================
    public Order outForDelivery(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.SHIPPED) {
            throw new RuntimeException("Order must be SHIPPED");
        }

        order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        return orderRepository.save(order);
    }

    // ================= DELIVER =================
    @Override
    public Order deliverOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.OUT_FOR_DELIVERY) {
            throw new RuntimeException("Order must be OUT_FOR_DELIVERY");
        }

        order.setOrderStatus(OrderStatus.DELIVERED);

        // ✅ ACTUAL delivery time
        order.setDeliveryDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // ================= CANCEL =================
    @Override
    public Order cancelOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel delivered order");
        }

        order.setOrderStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }

    // ================= DELETE =================
    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }
}