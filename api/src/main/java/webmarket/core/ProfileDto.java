package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Model.
 */
@Schema(description = "Модель пользователя")
public class ProfileDto {

    /**
     * Username.
     */
    @Schema(description = "Имя пользователя", required = true, example = "Иван Сергеевич")
    private String username;

    public ProfileDto() {
    }

    public ProfileDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
