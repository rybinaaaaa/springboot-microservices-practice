package com.rybina.order_service.service;

import com.rybina.order_service.dto.OrderCreateDto;
import com.rybina.order_service.dto.OrderLineItemsDto;
import com.rybina.order_service.model.Order;
import com.rybina.order_service.model.OrderLineItems;
import com.rybina.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void save(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderLineItemsList(
                orderCreateDto.getOrderLineItemsDtoList()
                        .stream()
                        .map(this::toOrderLineItems).toList());

        orderRepository.save(order);
    }

    private OrderLineItems toOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
