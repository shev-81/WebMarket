package webmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ответа")
public class StringResponse {

    @Schema(description = "Сгенерированнный CartUuid", required = true, example = "kjhdfa98lLIlijsdo9s978sd0dslksdk")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public StringResponse() {
    }
}
