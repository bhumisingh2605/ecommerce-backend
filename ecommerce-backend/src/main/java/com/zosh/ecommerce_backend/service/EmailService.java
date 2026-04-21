package com.zosh.ecommerce_backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderConfirmation(String toEmail, String orderId) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Order Placed Successfully");
        message.setText(
                "Your order has been placed successfully!\n\n" +
                        "Order ID: " + orderId + "\n" +
                        "Thank you for shopping with us!"
        );

        mailSender.send(message);
    }
}