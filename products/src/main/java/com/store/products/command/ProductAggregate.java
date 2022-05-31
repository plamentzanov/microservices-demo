package com.store.products.command;

import com.store.shared.commands.ReserveProductCommand;
import com.store.shared.core.events.ProductReservedEvent;
import com.store.products.core.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){};

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) throws Exception{
        if(quantity < reserveProductCommand.getQuantity()) {
            throw new Exception("Insufficient number of items in stock");
        }

        ProductReservedEvent productReservedEvent = new ProductReservedEvent();
        BeanUtils.copyProperties(reserveProductCommand, productReservedEvent);

        AggregateLifecycle.apply(productReservedEvent);

    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        productId = productCreatedEvent.getProductId();
        title = productCreatedEvent.getTitle();
        price = productCreatedEvent.getPrice();
        quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.quantity -= productReservedEvent.getQuantity();
    }

}
