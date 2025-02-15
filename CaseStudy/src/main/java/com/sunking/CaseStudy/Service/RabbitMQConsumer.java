package com.sunking.CaseStudy.Service;

import com.sunking.CaseStudy.DTO.OrderRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "orderQueue")
    public void processOrder(@Payload OrderRequest orderRequest) {
        try {
            orderService.processOrder(orderRequest);
            System.out.println("Order processed successfully: " + orderRequest);
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
        }
    }
}
