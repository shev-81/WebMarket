package com.webmarket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import webmarket.core.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Basket model.
 */
@Data
@Schema(description = "Корзина продуктов")
public class Cart {

    /**
     * List of products.
     */
    @Schema(description = "Список продуктов", required = true)
    private List<CartItem> items;

    /**
     * Basket price.
     */
    @Schema(description = "Цена корзины", required = true, example = "122.21")
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds the product to the cart.
     * @param productDto
     */
    public void add(ProductDto productDto) {
        if (add(productDto.getId())) {
            return;
        }
        items.add(new CartItem(productDto));
        recalculate();
    }

    public boolean add(Long id) {
        for (CartItem o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    /**
     * Reduces the number of products in the product position.
     * @param productId
     */
    public void decrement(Long productId) {
        Iterator<CartItem> iter = items.iterator();
        while (iter.hasNext()) {
            CartItem o = iter.next();
            if (o.getProductId().equals(productId)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    /**
     * Removes a product from the shopping cart.
     * @param productId
     */
    public void remove(Long productId) {
        items.removeIf(o -> o.getProductId().equals(productId));
        recalculate();
    }

    /**
     * Clears the trash.
     */
    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    /**
     * Recalculates the total cost of the basket.
     */
    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem o : items) {
            totalPrice = totalPrice.add(o.getPrice());
        }
    }

    /**
     * Combines data from the trash of an unregistered user
     * with the basket of the registered user.
     * @param another
     */
    public void merge(Cart another) {
        for (CartItem anotherItem : another.items) {
            boolean merged = false;
            for (CartItem myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}