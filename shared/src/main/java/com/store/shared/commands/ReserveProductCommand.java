package com.store.shared.commands;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Getter
@Setter
public class ReserveProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
}
