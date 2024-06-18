package com.rybina.inventory_service.repository;

import com.rybina.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySkuCode(String skuCode);

    @Query("from Inventory i where i.skuCode in :skuCode and i.quantity > 0")
    List<Inventory> findBySkuCodeInStock(List<String> skuCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
