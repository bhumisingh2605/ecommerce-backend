package com.zosh.ecommerce_backend.controller;

import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public User getUserProfile(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email);
    }
}