package com.store.orders.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ApproveOrderCommand {

    @TargetAggregateIdentifier
    private String id;
}
