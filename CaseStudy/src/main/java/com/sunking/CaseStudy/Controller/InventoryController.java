package com.sunking.CaseStudy.Controller;

import com.sunking.CaseStudy.DTO.InventoryRequest;
import com.sunking.CaseStudy.DTO.InventoryResponse;
import com.sunking.CaseStudy.Entity.Inventory;
import com.sunking.CaseStudy.Repository.InventoryRepository;
import com.sunking.CaseStudy.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable UUID productId) {
        Inventory inventory = inventoryService.getStock(productId);
        InventoryResponse response = new InventoryResponse(inventory.getProductId(), inventory.getStock());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateStock(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.updateStock(inventoryRequest);
        return ResponseEntity.ok("Stock updated.");
    }

    @GetMapping
    public List<InventoryResponse> getAll(){
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream()
                .map(inventory -> new InventoryResponse(inventory.getProductId(), inventory.getStock()))
                .collect(Collectors.toList());
    }
}

