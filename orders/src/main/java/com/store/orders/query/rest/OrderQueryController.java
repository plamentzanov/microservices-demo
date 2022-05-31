package com.store.orders.query.rest;

import com.store.orders.query.FindOrderQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

    private final QueryGateway queryGateway;

    @Autowired
    public OrderQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{id}")
    public OrderResponseRestModel getOrder(@PathVariable String id) {
        FindOrderQuery findOrderQuery = new FindOrderQuery();
        findOrderQuery.setId(id);

        return queryGateway.query(findOrderQuery, ResponseTypes.instanceOf(OrderResponseRestModel.class)).join();
    }
}
