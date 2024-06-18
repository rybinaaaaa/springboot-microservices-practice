package com.rybina.order_service.service;

import com.rybina.order_service.dto.InventoryResponse;
import com.rybina.order_service.dto.OrderCreateDto;
import com.rybina.order_service.dto.OrderLineItemsDto;
import com.rybina.order_service.model.Order;
import com.rybina.order_service.model.OrderLineItems;
import com.rybina.order_service.repository.OrderRepository;
import com.rybina.order_service.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Properties properties;

    @Transactional
    public void save(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderLineItemsList(
                orderCreateDto.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::toOrderLineItems).toList());

//        TODO: Call inventory service and save the order only if the product is un stock

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri(properties.getInventoryServicePath(),
                        uriBuilder -> uriBuilder.queryParam("sku_code", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) // to read the data from response
                .block(); // to make synchronous connection

        boolean areProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getInStock) && inventoryResponses.length == skuCodes.size();

        if (areProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order is not in stock!");
        }
    }

    private OrderLineItems toOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
