package com.webmarket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webmarket.core.ProductDto;

import java.math.BigDecimal;

/**
 * Модель позиции в корзине.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Позиция в списке корзины")
public class CartItem {

    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;

    @Schema(description = "Наименование продукта", required = true, example = "Яблоко")
    private String productName;

    @Schema(description = "Колилчество одного продукта", required = true, example = "7")
    private int quantity;

    @Schema(description = "Цена за одну единицу продукта", required = true, example = "122.21")
    private BigDecimal pricePerProduct;

    @Schema(description = "Цена за одну единицу продукта умноженную на колличество", required = true, example = "855.47")
    private BigDecimal price;

    public CartItem(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productName = productDto.getName();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
