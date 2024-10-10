package com.jlarcher.supermarketapi.service;


import com.jlarcher.supermarketapi.model.Product;
import com.jlarcher.supermarketapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImp {



    @Autowired
    private ProductRepository productRepository;


    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


    public Product save(Product product) {
        return productRepository.save(product);
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }



}
