package com.rybina.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rybina.product_service.ProductServiceApplicationTests;
import com.rybina.product_service.dto.ProductCreateDto;
import com.rybina.product_service.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest extends ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createsProductAndReturnsOk() throws Exception {
        ProductCreateDto productCreateDto = getProductCreateDto();

        long size = productRepository.count();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertThat(productRepository.count()).isEqualTo(size + 1);
    }

    @Test
    void returnsAllProducts() {
//        TODO
    }

    private static ProductCreateDto getProductCreateDto() {
        return ProductCreateDto.builder()
                .name("test")
                .description("test")
                .price(BigDecimal.valueOf(100))
                .build();
    }
}