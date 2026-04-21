package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Review;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest request, User user)throws ProductException;

    public List<Review> getAllReview(Long productId);
}
