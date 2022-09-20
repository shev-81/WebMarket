package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * The model of the item in the order.
 */
@Schema(description = "Модель позиции в заказе")
public class OrderItemDto {

    /**
     * Product ID.
     */
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;

    /**
     * Product Name.
     */
    @Schema(description = "Наименование продукта", required = true, example = "Яблоко")
    private String productName;

    /**
     * Quantity.
     */
    @Schema(description = "Колличество", required = true, example = "7")
    private int quantity;

    /**
     * Unit price.
     */
    @Schema(description = "Цена за единицу", required = true, example = "122.21")
    private BigDecimal pricePerProduct;

    /**
     * Цена за единицу помноженная на колличество.
     */
    @Schema(description = "Цена за единицу помноженная на колличество", required = true, example = "855,47")
    private BigDecimal price;

    public OrderItemDto(Long productId, String productName, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }

    public OrderItemDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
