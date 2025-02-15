package com.sunking.CaseStudy.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class InventoryResponse {
    private UUID productId;
    private int stock;
}
