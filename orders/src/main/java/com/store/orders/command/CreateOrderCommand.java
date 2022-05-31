package com.store.orders.command;

import com.store.orders.core.data.OrderStatus;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String id;
    private String productId;
    private int quantity;
    private String address;
    private OrderStatus orderStatus;
}
