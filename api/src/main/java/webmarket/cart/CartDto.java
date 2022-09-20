package webmarket.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO Basket Model.
 */
@Schema(description = "Модель DTO корзины")
public class CartDto {

    /**
     * List of products.
     */
    @Schema(description = "Список продуктов", required = true)
    private List<CartItemDto> items;

    /**
     * The price of the products in the basket.
     */
    @Schema(description = "Цена продуктов в корзине", required = true, example = "738.72")
    private BigDecimal totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
