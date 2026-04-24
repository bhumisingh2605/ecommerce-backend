package com.zosh.ecommerce_backend.repository;

import com.zosh.ecommerce_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
