package com.store.orders.command;

import com.store.orders.core.data.OrderStatus;
import com.store.orders.core.events.OrderApprovedEvent;
import com.store.orders.core.events.OrderCreatedEvent;
import com.store.orders.core.events.OrderRejectedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String id;
    private String productId;
    private int quantity;
    private String address;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand) {
        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent();
        BeanUtils.copyProperties(approveOrderCommand, orderApprovedEvent);

        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand) {
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent();
        BeanUtils.copyProperties(rejectOrderCommand, orderRejectedEvent);

        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        id = orderCreatedEvent.getId();
        productId = orderCreatedEvent.getProductId();
        quantity = orderCreatedEvent.getQuantity();
        address = orderCreatedEvent.getAddress();
        orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        orderStatus = OrderStatus.APPROVED;
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        orderStatus = OrderStatus.REJECTED;
    }

}
