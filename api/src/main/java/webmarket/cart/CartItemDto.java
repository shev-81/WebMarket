package webmarket.cart;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель позиции ( DTO ) в корзине")
public class CartItemDto {

    @Schema(description = "ID продукта", required = true, example = "3")
    private Long productId;

    @Schema(description = "Наименование продукта", required = true, example = "Яблоко")
    private String productTitle;

    @Schema(description = "Колличество продуктов в позиции", required = true, example = "6")
    private int quantity;

    @Schema(description = "Цена за единицу продукта", required = true, example = "123.12")
    private BigDecimal pricePerProduct;

    @Schema(description = "Цена позиции с расчетом (колличества помноженного на цену за единицу)", required = true, example = "738.72")
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
