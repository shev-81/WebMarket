package com.webmarket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webmarket.core.ProductDto;

import java.math.BigDecimal;

/**
 * The model of the position in the basket.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Позиция в списке корзины")
public class CartItem {

    /**
     * Product ID.
     */
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;

    /**
     * Product name.
     */
    @Schema(description = "Наименование продукта", required = true, example = "Яблоко")
    private String productName;

    /**
     * Quantity of one product.
     */
    @Schema(description = "Колилчество одного продукта", required = true, example = "7")
    private int quantity;

    /**
     * Price per unit of product.
     */
    @Schema(description = "Цена за одну единицу продукта", required = true, example = "122.21")
    private BigDecimal pricePerProduct;

    /**
     * The price per unit of product multiplied by the quantity.
     */
    @Schema(description = "Цена за одну единицу продукта умноженную на колличество", required = true, example = "855.47")
    private BigDecimal price;

    public CartItem(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productName = productDto.getName();
        this.quantity = 1;
        this.pricePerProduct = productDto.getPrice();
        this.price = productDto.getPrice();
    }

    /**
     * Changes the quantity of the product.
     * @param delta
     */
    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
