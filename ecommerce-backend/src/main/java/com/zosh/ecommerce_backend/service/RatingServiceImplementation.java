package com.zosh.ecommerce_backend.service;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Product;
import com.zosh.ecommerce_backend.model.Rating;
import com.zosh.ecommerce_backend.model.User;
import com.zosh.ecommerce_backend.repository.RatingRepository;
import com.zosh.ecommerce_backend.request.RatingRequest;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class RatingServiceImplementation implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository,
                                       ProductService productService  ) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;

    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
