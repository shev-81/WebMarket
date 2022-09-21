package webmarket.cart;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * The position model ( DTO ) in the cart.
 */
@Schema(description = "The position model ( DTO ) in the cart")
public class CartItemDto {

    /**
     * Product ID
     */
    @Schema(description = "Product ID", required = true, example = "3")
    private Long productId;

    /**
     * Product Name.
     */
    @Schema(description = "Product Name", required = true, example = "Apple")
    private String productTitle;

    /**
     * Number of products in a position.
     */
    @Schema(description = "Number of products in a position", required = true, example = "6")
    private int quantity;

    /**
     * Price per unit of product.
     */
    @Schema(description = "Price per unit of product", required = true, example = "123.12")
    private BigDecimal pricePerProduct;

    /**
     * Position price with calculation (quantity multiplied by unit price).
     */
    @Schema(description = "Position price with calculation (quantity multiplied by unit price)", required = true, example = "738.72")
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }
}
