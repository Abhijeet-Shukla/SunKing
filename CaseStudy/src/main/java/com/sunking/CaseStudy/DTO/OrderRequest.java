package com.sunking.CaseStudy.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequest {
    private UUID productId;
    private int quantity;
}
