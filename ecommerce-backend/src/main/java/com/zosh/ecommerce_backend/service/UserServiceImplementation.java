package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.config.JwtProvider;
import com.zosh.ecommerce_backend.exception.UserException;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
   this.userRepository=userRepository;
   this.jwtProvider =jwtProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not found with id: "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email=jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UserException("user not found with email" +email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
