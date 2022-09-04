package webmarket.analitic;

public class StatisticDto {
    private String nameProduct;
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
