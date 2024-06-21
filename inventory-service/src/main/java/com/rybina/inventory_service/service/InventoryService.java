package com.rybina.inventory_service.service;

import com.rybina.inventory_service.dto.InventoryResponse;
import com.rybina.inventory_service.model.Inventory;
import com.rybina.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

//    public boolean isInStock(String skuCode) {
//        return inventoryRepository.findBySkuCode(skuCode)
//                .map(inventory ->
//                        inventory.getQuantity() > 0)
//                .orElse(false);
//    }
//
//    public List<InventoryResponse> findAllInStock(List<String> skuCode) {
//        return inventoryRepository.findBySkuCodeInStock(skuCode)
//                .stream().map(InventoryService::getInventoryResponse).toList();
//    }

    @SneakyThrows
    public List<InventoryResponse> findBySkuCode(List<String> skuCodes) {
////        EXAMPLE FOT TIMELIMITER
//        log.info("Wait started");
//        Thread.sleep(10000);
//        log.info("Wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream().map(InventoryService::getInventoryResponse).toList();
    }

    private static InventoryResponse getInventoryResponse(Inventory inventory) {
        return new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0);
    }
}
