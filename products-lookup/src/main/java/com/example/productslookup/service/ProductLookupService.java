package com.example.productslookup.service;

import com.example.productslookup.data.ProductLookup;

public interface ProductLookupService {

    ProductLookupDto getProductLookupByIdOrTitle(String id, String title);

    void addProductLookup(ProductLookupDto productLookupDto);

}
