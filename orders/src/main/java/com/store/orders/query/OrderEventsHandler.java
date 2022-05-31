package com.store.orders.query;

import com.store.orders.core.data.Order;
import com.store.orders.core.data.OrderStatus;
import com.store.orders.core.data.OrdersRepository;
import com.store.orders.core.events.OrderApprovedEvent;
import com.store.orders.core.events.OrderCreatedEvent;
import com.store.orders.core.events.OrderRejectedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderEventsHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        Order order = new Order();
        BeanUtils.copyProperties(orderCreatedEvent, order);

        ordersRepository.save(order);
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) throws Exception{
        Order order = getOrder(orderApprovedEvent.getId());
        order.setOrderStatus(OrderStatus.APPROVED);

        ordersRepository.save(order);
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) throws Exception{
        Order order = getOrder(orderRejectedEvent.getId());
        order.setOrderStatus(OrderStatus.REJECTED);

        ordersRepository.save(order);
    }

    private Order getOrder(String orderId) throws Exception{
        Optional<Order> orderOptional = ordersRepository.findById(orderId);
        if(orderOptional.isEmpty()) {
            throw new Exception(String.format("Order with id %s not found", orderId));
        }

        return orderOptional.get();
    }
}
