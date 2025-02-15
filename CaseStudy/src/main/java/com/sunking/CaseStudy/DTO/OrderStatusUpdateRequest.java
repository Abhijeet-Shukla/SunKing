package com.sunking.CaseStudy.DTO;

import com.sunking.CaseStudy.Entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderStatusUpdateRequest {
    private UUID orderId;
    private OrderStatus status;
}
