package com.store.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final Environment environment;

    @Autowired
    public OrdersController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public String getOrders() {
        return "Server port: " + environment.getProperty("local.server.port");
    }
}
