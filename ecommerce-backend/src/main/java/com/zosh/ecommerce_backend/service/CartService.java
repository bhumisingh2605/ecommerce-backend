package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Cart;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public Cart addCartItem(Long userId, AddItemRequest request) throws ProductException;

    public Cart findUserCart(Long userId);

}
