package com.webmarket.validators;

import com.webmarket.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator of product fields DTO
 */
@Component
public class ProductValidator {
    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice().compareTo(BigDecimal.ONE) < 0) {
            errors.add("Цена продукта не может быть меньше 1");
        }
        if (productDto.getName().isBlank()) {
            errors.add("Продукт не может иметь пустое название");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
