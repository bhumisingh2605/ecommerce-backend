package com.zosh.ecommerce_backend.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public Order createOrder(int amount) throws RazorpayException {

        // ✅ Initialize Razorpay client
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        // ✅ Create order request
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // convert to paise
        options.put("currency", "INR");
        options.put("receipt", "order_" + System.currentTimeMillis());
        options.put("payment_capture", 1); // auto capture payment

        // ✅ Create and return order
        return client.orders.create(options);
    }
}