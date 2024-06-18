package com.rybina.product_service.service;

import com.rybina.product_service.dto.ProductCreateDto;
import com.rybina.product_service.dto.ProductReadEditDto;
import com.rybina.product_service.model.Product;
import com.rybina.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductCreateDto productCreateDto) {
        Product product = mapToProduct(productCreateDto);

        productRepository.save(product);
        log.info("Product with id {} is saved", product.getId());
    }


    public List<ProductReadEditDto> findAll() {
        return productRepository.findAll().stream().map((ProductService::mapToReadEditDto)).toList();
    }

    private static ProductReadEditDto mapToReadEditDto(Product product) {
        return ProductReadEditDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private static Product mapToProduct(ProductCreateDto productCreateDto) {
        return Product.builder()
                .name(productCreateDto.getName())
                .description(productCreateDto.getDescription())
                .price(productCreateDto.getPrice())
                .build();
    }
}
