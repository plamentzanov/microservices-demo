package com.store.orders.saga;

import com.store.shared.commands.ReserveProductCommand;
import com.store.shared.core.events.ProductReservedEvent;
import com.store.orders.command.ApproveOrderCommand;
import com.store.orders.command.RejectOrderCommand;
import com.store.orders.core.events.OrderApprovedEvent;
import com.store.orders.core.events.OrderCreatedEvent;
import com.store.orders.core.events.OrderRejectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderCreatedEvent orderCreatedEvent) {

        String orderId = orderCreatedEvent.getId();

        ReserveProductCommand reserveProductCommand = new ReserveProductCommand();
        reserveProductCommand.setProductId(orderCreatedEvent.getProductId());
        reserveProductCommand.setOrderId(orderId);
        reserveProductCommand.setQuantity(orderCreatedEvent.getQuantity());

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
           if(commandResultMessage.isExceptional()) {
               RejectOrderCommand rejectOrderCommand = new RejectOrderCommand();
               rejectOrderCommand.setId(orderId);
               commandGateway.send(rejectOrderCommand);
           }
        });
    }

    @SagaEventHandler(associationProperty = "orderId", keyName = "id")
    public void handle(ProductReservedEvent productReservedEvent) {
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand();
        approveOrderCommand.setId(productReservedEvent.getOrderId());

        commandGateway.send(approveOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        SagaLifecycle.end();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        SagaLifecycle.end();
    }
}
