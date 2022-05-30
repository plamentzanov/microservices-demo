package com.store.products.command.rest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductResponseRestModel {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
