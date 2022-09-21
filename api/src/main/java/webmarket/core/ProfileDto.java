package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Model.
 */
@Schema(description = "User Model")
public class ProfileDto {

    /**
     * Username.
     */
    @Schema(description = "Username", required = true, example = "Ivan Sergeevich")
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
