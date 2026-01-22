package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. Get all products (So you can see what you have)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Add a product (The new feature)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}