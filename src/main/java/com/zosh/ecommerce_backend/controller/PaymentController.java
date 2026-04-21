package com.zosh.ecommerce_backend.controller;

import com.razorpay.Order;
import com.zosh.ecommerce_backend.service.RazorpayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    // ✅ CREATE ORDER (FIXED)
    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) throws Exception {

        int amount = (int) data.get("amount");

        Order order = razorpayService.createOrder(amount);

        // ✅ IMPORTANT: return proper JSON
        JSONObject json = order.toJson();

        return ResponseEntity.ok(json.toMap());
    }
}