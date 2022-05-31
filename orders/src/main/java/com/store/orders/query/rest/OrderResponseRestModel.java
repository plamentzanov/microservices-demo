package com.store.orders.query.rest;

import com.store.orders.core.data.OrderStatus;
import lombok.Data;

@Data
public class OrderResponseRestModel {
    private String id;
    private String productId;
    private int quantity;
    private String address;
    private OrderStatus orderStatus;
}
