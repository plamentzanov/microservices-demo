package com.example.productslookup.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLookupRepository extends JpaRepository<ProductLookup, String> {

    ProductLookup findByIdOrTitle(String id, String title);
}
