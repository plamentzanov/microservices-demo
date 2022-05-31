package com.store.products.core.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("products-lookup-service")
public interface ProductsLookupClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products_lookup")
    ProductLookupDto getProductLookup(@RequestParam String id, @RequestParam String title);

    @RequestMapping(method = RequestMethod.POST, value = "/products_lookup")
    void addProductLookup(@RequestBody ProductLookupDto productLookupDto);
}
