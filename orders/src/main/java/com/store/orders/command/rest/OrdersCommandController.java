package com.store.orders.command.rest;

import com.store.orders.command.CreateOrderCommand;
import com.store.orders.core.data.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public OrdersCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<CreateOrderCommandResponseModel> createOrder(@Valid @RequestBody CreateOrderCommandRestModel orderCommandRestModel) {

        CreateOrderCommand createOrderCommand = new CreateOrderCommand();
        BeanUtils.copyProperties(orderCommandRestModel, createOrderCommand);
        createOrderCommand.setId(UUID.randomUUID().toString());
        createOrderCommand.setOrderStatus(OrderStatus.CREATED);
        String orderId = commandGateway.sendAndWait(createOrderCommand);

        CreateOrderCommandResponseModel responseModel = new CreateOrderCommandResponseModel();
        responseModel.setOrderId(orderId);

        return ResponseEntity.ok(responseModel);
    }
}
