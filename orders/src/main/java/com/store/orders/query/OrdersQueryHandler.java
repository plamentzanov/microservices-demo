package com.store.orders.query;

import com.store.orders.core.data.Order;
import com.store.orders.core.data.OrdersRepository;
import com.store.orders.query.rest.OrderResponseRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrdersQueryHandler {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersQueryHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @QueryHandler
    public OrderResponseRestModel findOrder(FindOrderQuery findOrderQuery) throws Exception {
        Optional<Order> optionalOrder = ordersRepository.findById(findOrderQuery.getId());

        if(optionalOrder.isEmpty()) {
            throw new Exception("Order not found");
        }

        OrderResponseRestModel orderResponseRestModel = new OrderResponseRestModel();
        BeanUtils.copyProperties(optionalOrder.get(), orderResponseRestModel);

        return orderResponseRestModel;
    }
}
