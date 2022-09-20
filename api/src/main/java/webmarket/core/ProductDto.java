package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Product model.
 */
@Schema(description = "Модель продукта")
public class ProductDto {

    /**
     * Product ID.
     */
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;

    /**
     * Product Name.
     */
    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
    private String name;

    /**
     * Product Price.
     */
    @Schema(description = "Цена продукта", required = true, example = "120.21")
    private BigDecimal price;

    public ProductDto(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductDto(String name) {
        this.name = name;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
