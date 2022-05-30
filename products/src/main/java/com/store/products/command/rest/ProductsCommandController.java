package com.store.products.command.rest;

import com.store.products.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProductsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponseRestModel> createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand createProductCommand = new CreateProductCommand();
        BeanUtils.copyProperties(createProductRestModel, createProductCommand);
        createProductCommand.setProductId(UUID.randomUUID().toString());

        String productId = commandGateway.sendAndWait(createProductCommand);

        CreateProductResponseRestModel responseRestModel = new CreateProductResponseRestModel();
        BeanUtils.copyProperties(createProductRestModel, responseRestModel);
        responseRestModel.setProductId(productId);

        return ResponseEntity.ok(responseRestModel);
    }
}
