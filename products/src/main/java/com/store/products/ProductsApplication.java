package com.store.products;

import com.store.shared.config.AxonConfig;
import com.store.products.command.interceptors.CreateProductCommandInterceptor;
import com.store.products.core.errorhandling.ProductsServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@Import({AxonConfig.class})
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context,
                                                        CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));

    }
    @Autowired
    public void configure(EventProcessingConfigurer config) {
        config.registerListenerInvocationErrorHandler("product-group",
                conf -> new ProductsServiceEventsErrorHandler());

    }

}
