package com.rybina.order_service.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:microservice_paths.properties")
@RequiredArgsConstructor
public class Properties {

    @Value("${path.inventory-service}")
    final String inventoryServicePath;
}
