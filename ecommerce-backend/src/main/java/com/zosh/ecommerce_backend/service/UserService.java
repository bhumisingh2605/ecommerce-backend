package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.UserException;
import com.zosh.ecommerce_backend.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    User findUserByEmail(String email);


}
