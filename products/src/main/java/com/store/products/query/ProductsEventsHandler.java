package com.store.products.query;

import com.store.shared.core.events.ProductReservedEvent;
import com.store.products.core.clients.ProductLookupDto;
import com.store.products.core.clients.ProductsLookupClient;
import com.store.products.core.data.Product;
import com.store.products.core.data.ProductsRepository;
import com.store.products.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("product-group")
public class ProductsEventsHandler {

    private final ProductsRepository productsRepository;
    private final ProductsLookupClient productsLookupClient;

    @Autowired
    public ProductsEventsHandler(ProductsRepository productsRepository, ProductsLookupClient productsLookupClient) {
        this.productsRepository = productsRepository;
        this.productsLookupClient = productsLookupClient;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);

        product.setId(event.getProductId());
        productsRepository.save(product);

        ProductLookupDto productLookupDto = new ProductLookupDto();
        productLookupDto.setTitle(product.getTitle());
        productLookupDto.setId(product.getId());
        productsLookupClient.addProductLookup(productLookupDto);
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) throws Exception {
        String productId = productReservedEvent.getProductId();
        Optional<Product> productOptional = productsRepository.findById(productId);
        if(productOptional.isEmpty()) {
            throw new Exception(String.format("Product with id %s not found", productId));
        }

        Product product = productOptional.get();
        product.setQuantity(product.getQuantity() - productReservedEvent.getQuantity());
        productsRepository.save(product);

    }
}
