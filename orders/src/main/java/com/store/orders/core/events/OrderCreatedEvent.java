package com.store.orders.core.events;

import com.store.orders.core.data.OrderStatus;
import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String id;
    private String productId;
    private int quantity;
    private String address;
    private OrderStatus orderStatus;
}
