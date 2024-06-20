package com.rybina.order_service.controller;

import com.rybina.order_service.dto.OrderCreateDto;
import com.rybina.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    "inventory" = name, which we wrote to the application.yaml -- resilience4j.circuitbreaker.instances.inventory; etc: resilience4j.circuitbreaker.instances.some_namelalala
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")

    /*
    Аннотация @TimeLimiter из библиотеки Resilience4j
    предназначена для работы с асинхронными методами и в
    озвращаемыми типами, такими как CompletableFuture.
    Она не может быть применена к синхронным методам.
    Если метод будет выполняться синхронно, TimeLimiter не сможет
    ограничить время его выполнения.
    */
    @TimeLimiter(name = "inventory") //to limit time of proceeding the request
    /*
    @Retry аннотация повтряет запрос еще [retry.instances.inventory.max-attempts: 3] раза перед тем как вернет 500 статус (если все разы время превысило лимиты)
     */
    @Retry(name = "inventory")
    public CompletableFuture<String> create(@RequestBody OrderCreateDto orderCreateDto) {

//        log.info("Order has been successfully created");
        return CompletableFuture.supplyAsync(() -> orderService.save(orderCreateDto));
    }

    public CompletableFuture<String> fallbackMethod(@RequestBody OrderCreateDto orderCreateDto, RuntimeException runtimeException) {

        log.info("{}: Something went wrong", this.getClass());
        return CompletableFuture.supplyAsync(() -> "Something went wrong, please order later");
    }
}