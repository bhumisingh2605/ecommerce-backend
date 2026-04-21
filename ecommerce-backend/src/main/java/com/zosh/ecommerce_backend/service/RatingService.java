package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Rating;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    public Rating createRating(RatingRequest req, User user)throws ProductException;
    public List<Rating> getProductsRating(Long productId);


}
