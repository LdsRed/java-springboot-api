package com.jlarcher.supermarketapi.repository;

import com.jlarcher.supermarketapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
