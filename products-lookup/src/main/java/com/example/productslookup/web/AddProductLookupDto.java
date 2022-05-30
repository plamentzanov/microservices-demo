package com.example.productslookup.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddProductLookupDto {

    @NotBlank
    private String id;
    @NotBlank
    private String title;
}
