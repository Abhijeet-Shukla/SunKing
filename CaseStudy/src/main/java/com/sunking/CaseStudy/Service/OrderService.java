package com.sunking.CaseStudy.Service;

import com.sunking.CaseStudy.DTO.OrderRequest;
import com.sunking.CaseStudy.DTO.OrderStatusUpdateRequest;
import com.sunking.CaseStudy.Entity.Inventory;
import com.sunking.CaseStudy.Entity.Order;
import com.sunking.CaseStudy.Entity.OrderStatus;
import com.sunking.CaseStudy.Exception.InsufficientStockException;
import com.sunking.CaseStudy.Exception.InvalidOrderRequestException;
import com.sunking.CaseStudy.Exception.OrderNotFoundException;
import com.sunking.CaseStudy.Repository.InventoryRepository;
import com.sunking.CaseStudy.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        if (orderRequest.getQuantity() <= 0) {
            throw new InvalidOrderRequestException("Order quantity must be greater than zero.");
        }

        Inventory inventory = inventoryRepository.findByProductId(orderRequest.getProductId())
                .orElseThrow(() -> new InsufficientStockException("Product not found in inventory."));

        if (inventory.getStock() <= 0) {
            throw new InsufficientStockException("Product is out of stock.");
        }

        if (inventory.getStock() < orderRequest.getQuantity()) {
            throw new InsufficientStockException("Not enough stock available. Available: " + inventory.getStock());
        }

        // Reduce stock
        inventory.setStock(inventory.getStock() - orderRequest.getQuantity());
        inventoryRepository.save(inventory);

        // Create order
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(UUID orderId, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        order.setStatus(request.getStatus()); // Update order status
        return orderRepository.save(order);   // Save the updated order
    }

    @Transactional
    @Retryable(
            retryFor = {Exception.class},  // Specify exceptions for retry
            maxAttempts = 5,              // Retry up to 5 times
            backoff = @Backoff(delay = 2000, multiplier = 2) // Exponential backoff (2s, 4s, 8s, 16s)
    )
    public void processOrder(OrderRequest orderRequest) {
        try {
            // Simulate occasional failure for testing
            if (Math.random() < 0.3) {
                throw new RuntimeException("Simulated failure");
            }

            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setProductId(orderRequest.getProductId());
            order.setQuantity(orderRequest.getQuantity());
            order.setStatus(OrderStatus.PENDING);

            orderRepository.save(order);
            System.out.println("✅ Order saved successfully: " + order.getId());
        } catch (Exception e) {
            System.err.println("❌ Order processing failed: " + e.getMessage());
            throw e; // Throw exception so retry mechanism triggers
        }
    }

    // Handle failure after max retries
    @Recover
    public void recoverFromFailure(Exception e, OrderRequest orderRequest) {
        System.err.println("Order permanently failed after retries: " + orderRequest);
    }
}