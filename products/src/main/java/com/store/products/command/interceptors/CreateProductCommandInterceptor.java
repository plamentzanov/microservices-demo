package com.store.products.command.interceptors;

import com.store.products.command.CreateProductCommand;
import com.store.products.core.clients.ProductLookupDto;
import com.store.products.core.clients.ProductsLookupClient;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductsLookupClient productsLookupClient;

    @Autowired
    public CreateProductCommandInterceptor(ProductsLookupClient productsLookupClient) {
        this.productsLookupClient = productsLookupClient;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if(CreateProductCommand.class.equals(command.getPayloadType())) {

                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();

                try {
                    ProductLookupDto productLookupDto = productsLookupClient
                            .getProductLookup(createProductCommand.getProductId(), createProductCommand.getTitle());

                    if(productLookupDto.getId() != null && productLookupDto.getTitle() != null) {
                        throwIllegalStateException(createProductCommand);
                    }

                } catch (Exception exception) {
                    throwIllegalStateException(createProductCommand);
                }
            }

            return command;
        };
    }

    private static void throwIllegalStateException(CreateProductCommand createProductCommand) {
        throw new IllegalStateException(
                String.format("Product with productId %s or title %s already exist",
                        createProductCommand.getProductId(), createProductCommand.getTitle())
        );
    }
}
