package com.sunking.CaseStudy.Service;

import com.sunking.CaseStudy.DTO.InventoryRequest;
import com.sunking.CaseStudy.Entity.Inventory;
import com.sunking.CaseStudy.Exception.InventoryNotAvailableException;
import com.sunking.CaseStudy.Repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public void reduceStock(UUID productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotAvailableException("Inventory not found for product ID: " + productId));

        if (inventory.getStock() < quantity) {
            throw new InventoryNotAvailableException("Insufficient stock for product ID: " + productId);
        }

        inventory.setStock(inventory.getStock() - quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void updateStock(InventoryRequest request) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(request.getProductId());

        Inventory inventory = optionalInventory.orElseGet(() -> new Inventory(request.getProductId(), 0));
        inventory.setStock(request.getStock());
        inventoryRepository.save(inventory);
    }
    public Inventory getStock(UUID productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotAvailableException("Inventory not found for product ID: " + productId));
    }
}
