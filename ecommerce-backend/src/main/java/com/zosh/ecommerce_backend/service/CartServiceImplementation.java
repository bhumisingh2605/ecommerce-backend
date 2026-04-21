package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Cart;
import com.zosh.ecommerce_backend.model.CartItem;
import com.zosh.ecommerce_backend.model.Product;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.repository.CartItemRepository;
import com.zosh.ecommerce_backend.repository.CartRepository;
import com.zosh.ecommerce_backend.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImplementation implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public CartServiceImplementation(CartRepository cartRepository,
                                     CartItemService cartItemService,
                                     ProductService productService,
                                     CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    // ✅ CREATE CART
    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());
        cart.setTotalItem(0);
        cart.setTotalPrice(0);
        cart.setTotalDiscountedPrice(0);
        cart.setDiscount(0);
        return cartRepository.save(cart);
    }

    // ✅ ADD ITEM TO CART
    @Override
    public Cart addCartItem(Long userId, AddItemRequest request) throws ProductException {

        // 🔥 Always ensure cart exists
        Cart cart = findUserCart(userId);

        Product product = productService.findProductById(request.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(
                cart, product, request.getSize(), userId
        );

        if (isPresent == null) {

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setSize(request.getSize());

            int price = request.getQuantity() * product.getDiscountedPrice();

            cartItem.setPrice(price);
            cartItem.setDiscountedPrice(price);

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);

            cart.getCartItems().add(createdCartItem);

        } else {
            // ✅ Update existing item
            isPresent.setQuantity(isPresent.getQuantity() + request.getQuantity());

            int newPrice = isPresent.getQuantity() * product.getDiscountedPrice();

            isPresent.setPrice(newPrice);
            isPresent.setDiscountedPrice(newPrice);

            cartItemRepository.save(isPresent);
        }

        // 🔥 IMPORTANT: update cart totals after adding item
        return findUserCart(userId);
    }

    // ✅ FIND USER CART (FULLY SAFE)
    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        // 🔥 Create cart if not exists
        if (cart == null) {
            cart = new Cart();

            User user = new User();
            user.setId(userId);

            cart.setUser(user);
            cart.setCartItems(new HashSet<>());
            cart.setTotalItem(0);
            cart.setTotalPrice(0);
            cart.setTotalDiscountedPrice(0);
            cart.setDiscount(0);

            cart = cartRepository.save(cart);
        }

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        // 🔥 NULL SAFE CHECK (VERY IMPORTANT)
        Set<CartItem> items = cart.getCartItems();
        if (items != null) {
            for (CartItem cartItem : items) {
                totalPrice += cartItem.getPrice();
                totalDiscountedPrice += cartItem.getDiscountedPrice();
                totalItem += cartItem.getQuantity();
            }
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}