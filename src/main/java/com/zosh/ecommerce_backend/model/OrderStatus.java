package com.zosh.ecommerce_backend.model;

public enum OrderStatus {
    CREATED,
    PLACED,
    CONFIRMED,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELED
}