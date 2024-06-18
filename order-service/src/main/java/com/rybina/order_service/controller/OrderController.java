package com.rybina.order_service.controller;

import com.rybina.order_service.dto.OrderCreateDto;
import com.rybina.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody OrderCreateDto orderCreateDto) {

        orderService.save(orderCreateDto);

        log.info("Order has been successfully created");
        return "Order has been successfully created";
    }
}