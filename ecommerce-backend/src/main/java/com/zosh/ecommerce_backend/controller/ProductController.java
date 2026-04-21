package com.zosh.ecommerce_backend.controller;

import com.zosh.ecommerce_backend.exception.ProductException;
import com.zosh.ecommerce_backend.model.Product;
import com.zosh.ecommerce_backend.request.CreateProductRequest;
import com.zosh.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.tomcat.autoconfigure.TomcatServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
                                                                      @RequestParam List<String>color, @RequestParam List<String> size, @RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
                                                                      @RequestParam String stock, @RequestParam(defaultValue = "0")Integer pageNumber, @RequestParam(defaultValue = "10")Integer pageSize) {
        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId)throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);

    }
    @PostMapping("/admin/products")
    public ResponseEntity<Product> createProductHandler(
            @RequestBody CreateProductRequest request
    ) {
        Product created = productService.createProduct(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    }


