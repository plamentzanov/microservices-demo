package com.example.productslookup.service;

import com.example.productslookup.data.ProductLookup;
import com.example.productslookup.data.ProductLookupRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLookupServiceImpl implements ProductLookupService {

    private final ProductLookupRepository productLookupRepository;

    @Autowired
    public ProductLookupServiceImpl(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @Override
    public ProductLookupDto getProductLookupByIdOrTitle(String id, String title) {
        ProductLookup productLookup = this.productLookupRepository.findByIdOrTitle(id, title);

        if(productLookup == null) {
            return null;
        }

        ProductLookupDto productLookupDtoResp = new ProductLookupDto();
        BeanUtils.copyProperties(productLookup, productLookupDtoResp);
        return productLookupDtoResp;
    }

    @Override
    public void addProductLookup(ProductLookupDto productLookupDto) {
        ProductLookup productLookup = new ProductLookup();
        BeanUtils.copyProperties(productLookupDto, productLookup);
        productLookupRepository.save(productLookup);
    }


}
