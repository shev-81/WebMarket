package webmarket.analitic;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO объект пересылаемый между сервисом статистики и сервисом корзины.
 */
@Schema(description = "Модель продукта в аналитике.")
public class StatisticDto {

    /**
     * Имя продукта.
     */
    @Schema(description = "Имя продукта", required = true)
    private String nameProduct;

    /**
     * Колличество взятое из корзины.
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
