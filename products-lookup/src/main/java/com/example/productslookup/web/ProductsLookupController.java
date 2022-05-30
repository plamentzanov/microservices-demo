package com.example.productslookup.web;

import com.example.productslookup.service.ProductLookupDto;
import com.example.productslookup.service.ProductLookupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/products_lookup")
public class ProductsLookupController {

    private final ProductLookupService productLookupService;

    @Autowired
    public ProductsLookupController(ProductLookupService productLookupService) {
        this.productLookupService = productLookupService;
    }


    @PostMapping
    public ResponseEntity<String> saveProductLookup(@Valid @RequestBody AddProductLookupDto productLookupDto) {

        if(productLookupService.getProductLookupByIdOrTitle(productLookupDto.getId(), productLookupDto.getTitle()) != null) {
            return ResponseEntity.internalServerError().body("Already added");
        }

        ProductLookupDto productLookup = new ProductLookupDto();
        BeanUtils.copyProperties(productLookupDto, productLookup);
        productLookupService.addProductLookup(productLookup);

        return ResponseEntity.ok("Success");
    }

    @GetMapping
    public ResponseEntity<ProductLookupDto> getProductLookup(@RequestParam String id, @RequestParam String title) {
        ProductLookupDto productLookupDto = productLookupService.getProductLookupByIdOrTitle(id, title);

        return ResponseEntity.ok(Objects.requireNonNullElseGet(productLookupDto, ProductLookupDto::new));

    }

}
