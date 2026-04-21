package com.zosh.ecommerce_backend.request;

import java.util.List;

public class OrderRequest {

    private AddressDTO address;
    private List<OrderItemDTO> items;   // ✅ FIX: REQUIRED FIELD

    // ================= ADDRESS DTO =================
    public static class AddressDTO {
        private String name;
        private String phone;
        private String address;
        private String city;
        private String state;
        private String pincode;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getPincode() { return pincode; }
        public void setPincode(String pincode) { this.pincode = pincode; }
    }

    // ================= ORDER ITEM DTO =================
    public static class OrderItemDTO {
        private Long productId;
        private Integer quantity;

        // ❌ REMOVED title & price (security + correctness)

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    // ================= GETTERS & SETTERS =================

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}