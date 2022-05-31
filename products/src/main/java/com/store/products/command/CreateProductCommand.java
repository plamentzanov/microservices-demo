package com.store.products.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
