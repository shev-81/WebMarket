package webmarket.analitic;

/**
 * DTO объект пересылаемый между сервисом статистики и сервисом корзины.
 */
public class StatisticDto {

    /**
     * Имя продукта.
     */
    private String nameProduct;

    /**
     * Колличество взятое из корзины.
     */
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
