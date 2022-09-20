package webmarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response model.
 */
@Schema(description = "Модель ответа")
public class StringResponse {

    /**
     * Generated CartUuid.
     */
    @Schema(description = "Generated CartUuid", required = true, example = "kjhdfa98lLIlijsdo9s978sd0dslksdk")
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
