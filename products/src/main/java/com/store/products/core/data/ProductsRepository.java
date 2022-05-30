package com.store.products.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, String> {
    Product findByTitleOrId(String title, String id);
}
