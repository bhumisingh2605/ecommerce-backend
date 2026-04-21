package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Product;
import com.zosh.ecommerce_backend.model.Review;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.repository.ProductRepository;
import com.zosh.ecommerce_backend.repository.ReviewRepository;
import com.zosh.ecommerce_backend.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository,
                                       ProductService productService,
                                       ProductRepository productRepository  ) {
        this.reviewRepository=reviewRepository;
        this.productService=productService;
    }

    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(review.getReview());
        review.setCreatedAt(LocalDateTime.now());


        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
