package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional // <--- CRITICAL: If anything fails, everything is rolled back.
    public Order placeOrder(Long userId, List<Long> productIds) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("COMPLETED");
        order.setTotalPrice(BigDecimal.ZERO); // Start at 0

        // Save order first to get an ID (needed for items)
        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (Long productId : productIds) {
            // 1. Find the product
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // 2. Check Inventory (The most important check)
            if (product.getStockQuantity() < 1) {
                throw new RuntimeException("Product " + product.getName() + " is OUT OF STOCK");
            }

            // 3. Deduct Stock
            product.setStockQuantity(product.getStockQuantity() - 1);
            productRepository.save(product);

            // 4. Add to Order Items
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1);
            item.setPriceAtPurchase(product.getPrice());

            // Add price to total
            total = total.add(product.getPrice());

            // (Note: In a real app, you'd save the item to a repository here or cascade it)
            // For simplicity with our setup, we are trusting the DB cascade or ignoring item persistence details for this specific step
            // to focus on the Stock Logic.
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }
}