package com.store.products.core.data;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {

    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    private BigDecimal price;

    @Column(columnDefinition = "integer default 25")
    private int quantity;

}
