package com.rybina.inventory_service.controller;

import com.rybina.inventory_service.dto.InventoryResponse;
import com.rybina.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> findInStock(@RequestParam("sku_code") List<String> skuCodes) {
        return inventoryService.findBySkuCode(skuCodes);
    }
}
