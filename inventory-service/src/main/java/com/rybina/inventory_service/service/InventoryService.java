package com.rybina.inventory_service.service;

import com.rybina.inventory_service.model.Inventory;
import com.rybina.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventory ->
                        inventory.getQuantity() > 0)
                .orElse(false);
    }
}
