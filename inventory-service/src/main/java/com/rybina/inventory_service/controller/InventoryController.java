package com.rybina.inventory_service.controller;

import com.rybina.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku_code}")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean isInStock(@PathVariable("sku_code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
