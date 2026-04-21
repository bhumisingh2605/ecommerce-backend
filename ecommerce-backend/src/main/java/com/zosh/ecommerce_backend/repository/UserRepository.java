package com.zosh.ecommerce_backend.repository;

import com.zosh.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);


}
