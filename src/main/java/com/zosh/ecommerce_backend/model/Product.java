package com.zosh.ecommerce_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;

    // ================= FIXED (USE WRAPPER TYPES) =================
    private Integer price;

    @Column(name = "discounted_price")
    private Integer discountedPrice = 0;

    @Column(name = "discount_percent")
    private Integer discountPercent = 0;

    @Column(name = "quantity")
    private Integer quantity = 0;

    @Column(name = "in_stock")
    private Boolean inStock = true;

    private String brand;
    private String color;

    @ElementCollection
    @CollectionTable(
            name = "product_sizes",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Size> sizes = new HashSet<>();

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    private Integer numRatings = 0;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    private LocalDateTime createdAt;

    // ================= BUSINESS LOGIC =================

    public void decreaseStock(int qty) {
        if (this.quantity == null) this.quantity = 0;

        this.quantity -= qty;

        if (this.quantity <= 0) {
            this.quantity = 0;
            this.inStock = false;
        }
    }

    public void increaseStock(int qty) {
        if (this.quantity == null) this.quantity = 0;

        this.quantity += qty;

        if (this.quantity > 0) {
            this.inStock = true;
        }
    }

    public boolean isAvailable(int qty) {
        return this.quantity != null && this.quantity >= qty;
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public Integer getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Integer discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.inStock = quantity != null && quantity > 0;
    }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Set<Size> getSizes() { return sizes; }
    public void setSizes(Set<Size> sizes) { this.sizes = sizes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Rating> getRatings() { return ratings; }
    public void setRatings(List<Rating> ratings) { this.ratings = ratings; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public Integer getNumRatings() { return numRatings; }
    public void setNumRatings(Integer numRatings) { this.numRatings = numRatings; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}