package com.store.products.command.rest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class CreateProductRestModel {
    @NotBlank(message = "Title cannot be null or blank")
    private String title;

    @Min(value = 1, message = "Price must be a positive number")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity must be a positive number")
    private int quantity;
}
