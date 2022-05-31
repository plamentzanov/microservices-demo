package com.store.orders.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class RejectOrderCommand {

    @TargetAggregateIdentifier
    private String id;
}
