package com.sunking.CaseStudy.Controller;

import com.sunking.CaseStudy.DTO.OrderRequest;
import com.sunking.CaseStudy.DTO.OrderStatusUpdateRequest;
import com.sunking.CaseStudy.Entity.Order;
import com.sunking.CaseStudy.Exception.InsufficientStockException;
import com.sunking.CaseStudy.Exception.InvalidOrderRequestException;
import com.sunking.CaseStudy.Repository.OrderRepository;
import com.sunking.CaseStudy.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        try {
            Order order = orderService.placeOrder(request);
            return ResponseEntity.ok(order);
        } catch (InvalidOrderRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while placing the order.");
        }
    }

    @PutMapping("/status")
    public ResponseEntity<Order> updateOrderStatus(@RequestBody OrderStatusUpdateRequest request) {
        Order updatedOrder = orderService.updateOrderStatus(request.getOrderId(), request);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
