package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.CartItemException;
import com.zosh.ecommerce_backend.exception.UserException;
import com.zosh.ecommerce_backend.model.Cart;
import com.zosh.ecommerce_backend.model.CartItem;
import com.zosh.ecommerce_backend.model.Product;
import jdk.jshell.spi.ExecutionControl;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id, CartItem cartItem)throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;



}
