package com.webmarket.converters;

import com.webmarket.entities.Product;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }
}
