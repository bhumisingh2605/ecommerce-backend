package com.zosh.ecommerce_backend.controller;

import com.zosh.ecommerce_backend.repository.CartItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    private final CartItemRepository cartItemRepository;

    public CartItemController(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // ✅ DELETE CART ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {

        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("Cart item not found");
        }

        cartItemRepository.deleteById(id);

        return ResponseEntity.ok("Cart item deleted successfully");
    }
}