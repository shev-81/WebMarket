package com.webmarket.converters;

import com.webmarket.entities.Product;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

/**
 * Конвертер сущности в ДТО и обратно.
 */
@Component
public class ProductConverter {

    /**
     * Конвертирует ДТО продукта в сущность продукт.
     * @param productDto
     * @return
     */
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice());
    }

    /**
     * Конвертирует сущность продукт в ДТО продукта.
     * @param product
     * @return
     */
    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }
}
