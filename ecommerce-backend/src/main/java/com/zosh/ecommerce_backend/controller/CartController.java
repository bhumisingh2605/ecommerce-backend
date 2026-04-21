package com.zosh.ecommerce_backend.controller;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.exception.UserException;
import com.zosh.ecommerce_backend.model.Cart;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.request.AddItemRequest;
import com.zosh.ecommerce_backend.service.CartService;
import com.zosh.ecommerce_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // ✅ GET USER CART
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCartHandler(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // ✅ ADD ITEM TO CART
    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addCartItemHandler(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest request
    ) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.addCartItem(user.getId(), request);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // ✅ CREATE CART (OPTIONAL - AUTO CREATE)
    @PostMapping("/cart/create")
    public ResponseEntity<Cart> createCartHandler(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.createCart(user);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
}