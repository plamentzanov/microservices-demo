package com.store.orders.command.rest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderCommandRestModel {

    @NotBlank(message = "Product Id is required")
    private String productId;

    @Min(value = 1, message = "Quantity is a required field")
    private int quantity;

    @NotBlank(message = "Address is a required field")
    private String address;

}
