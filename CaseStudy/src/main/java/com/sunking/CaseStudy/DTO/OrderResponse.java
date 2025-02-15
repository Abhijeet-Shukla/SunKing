package com.sunking.CaseStudy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private UUID orderId;
    private String message;
}
