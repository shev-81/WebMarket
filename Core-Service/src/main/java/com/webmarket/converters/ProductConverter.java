package com.webmarket.converters;

import com.webmarket.entities.Product;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

/**
 * Entity converter to DTO and back.
 */
@Component
public class ProductConverter {

    /**
     * Converts the DTO of the product into the essence of the product.
     * @param productDto
     * @return
     */
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }

    /**
     * Converts the essence of the product into the DTO of the product.
     * @param product
     * @return
     */
    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }
}
