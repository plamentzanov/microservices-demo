package com.store.orders.core.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderRejectedEvent {

    @TargetAggregateIdentifier
    private String id;
}
