package com.zosh.ecommerce_backend.request;

import com.zosh.ecommerce_backend.model.Size;
import java.util.Set;

public class CreateProductRequest {

    private String imageUrl;
    private String brand;
    private String title;
    private String color;
    private int discountedPrice;
    private int price;
    private int discountPercent;
    private Set<Size> sizes;
    private int quantity;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
    private String description;

    // GETTERS & SETTERS

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(int discountedPrice) { this.discountedPrice = discountedPrice; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }

    public Set<Size> getSizes() { return sizes; }
    public void setSizes(Set<Size> sizes) { this.sizes = sizes; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTopLevelCategory() { return topLevelCategory; }
    public void setTopLevelCategory(String topLevelCategory) { this.topLevelCategory = topLevelCategory; }

    public String getSecondLevelCategory() { return secondLevelCategory; }
    public void setSecondLevelCategory(String secondLevelCategory) { this.secondLevelCategory = secondLevelCategory; }

    public String getThirdLevelCategory() { return thirdLevelCategory; }
    public void setThirdLevelCategory(String thirdLevelCategory) { this.thirdLevelCategory = thirdLevelCategory; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}