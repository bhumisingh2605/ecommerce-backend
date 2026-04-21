package com.zosh.ecommerce_backend.controller;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.*;
import com.zosh.ecommerce_backend.request.OrderRequest;
import com.zosh.ecommerce_backend.service.OrderService;
import com.zosh.ecommerce_backend.service.ProductService;
import com.zosh.ecommerce_backend.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public OrderController(OrderService orderService,
                           UserService userService,
                           ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    // ================= GET CURRENT USER =================
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.findUserByEmail(email);
    }

    // ================= CREATE ORDER =================
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest req) {

        try {
            User user = getCurrentUser();

            if (req.getItems() == null || req.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Cart is empty");
            }

            if (req.getAddress() == null) {
                return ResponseEntity.badRequest().body("Address is required");
            }

            // ================= ADDRESS SAFE =================
            OrderRequest.AddressDTO addr = req.getAddress();

            Address address = new Address();
            address.setFirstName(addr.getName() != null ? addr.getName() : "User");
            address.setMobile(addr.getPhone());
            address.setStreetAddress(addr.getAddress());
            address.setCity(addr.getCity());
            address.setState(addr.getState());
            address.setZipCode(addr.getPincode());

            // ================= ORDER =================
            Order order = new Order();
            order.setUser(user);
            order.setShippingAddress(address);
            order.setOrderStatus(OrderStatus.PLACED);

            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0;
            int totalItems = 0;

            // ================= ITEMS =================
            for (OrderRequest.OrderItemDTO item : req.getItems()) {

                Product product;
                try {
                    product = productService.findProductById(item.getProductId());
                } catch (ProductException e) {
                    return ResponseEntity.badRequest()
                            .body("Product not found: " + item.getProductId());
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(product.getDiscountedPrice());

                orderItems.add(orderItem);

                totalPrice += product.getDiscountedPrice() * item.getQuantity();
                totalItems += item.getQuantity();
            }

            order.setOrderItems(orderItems);
            order.setTotalPrice(totalPrice);
            order.setTotalItem(totalItems);

            Order savedOrder = orderService.saveOrder(order);

            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Order failed: " + e.getMessage());
        }
    }

    // ================= USER ORDERS =================
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrders() {

        User user = getCurrentUser();

        return ResponseEntity.ok(
                orderService.getUsersOrders(user.getId())
        );
    }

    // ================= ALL ORDERS =================
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id)
            throws Exception {

        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    // ================= CANCEL ORDER =================
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id)
            throws Exception {

        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id)
            throws Exception {

        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}