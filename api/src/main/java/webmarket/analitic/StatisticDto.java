package webmarket.analitic;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO is an object sent between the statistics service and the bucket service.
 */
@Schema(description = "Модель продукта в аналитике.")
public class StatisticDto {

    /**
     * Product name.
     */
    @Schema(description = "Имя продукта", required = true)
    private String nameProduct;

    /**
     * The quantity taken from the basket.
     */
    @Schema(description = "Количество продуктов", required = true)
    private Integer count;

    public StatisticDto(String nameProduct, Integer count) {
        this.nameProduct = nameProduct;
        this.count = count;
    }

    public StatisticDto() {
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
