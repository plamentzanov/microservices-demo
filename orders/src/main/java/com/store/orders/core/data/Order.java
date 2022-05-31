package com.store.orders.core.data;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;
    private String productId;
    private int quantity;
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
