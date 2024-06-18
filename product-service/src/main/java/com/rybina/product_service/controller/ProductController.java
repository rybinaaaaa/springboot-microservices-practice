package com.rybina.product_service.controller;

import com.rybina.product_service.dto.ProductCreateDto;
import com.rybina.product_service.dto.ProductReadEditDto;
import com.rybina.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductCreateDto productCreateDto) {
        productService.createProduct(productCreateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadEditDto> readAllProducts() {
        return productService.findAll();
    }
}
