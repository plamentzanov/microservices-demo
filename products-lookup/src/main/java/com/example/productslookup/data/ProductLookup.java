package com.example.productslookup.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_lookup")
@Getter
@Setter
@RequiredArgsConstructor
public class ProductLookup {
    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

}
