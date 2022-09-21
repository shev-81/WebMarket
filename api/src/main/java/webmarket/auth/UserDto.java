package webmarket.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The user's DTO.
 */
@Schema(description = "The user's DTO.")
public class UserDto {

    /**
     * User ID
     */
    @Schema(description = "User ID", required = true, example = "3")
    private Long id;

    /**
     * Username
     */
    @Schema(description = "Username", required = true, example = "Ivan")
    private String name;

    /**
     * User's last name
     */
    @Schema(description = "User's last name", required = true, example = "Ivanov")
    private String fio;

    /**
     * User roles
     */
    @Schema(description = "User roles", required = true, example = "ROLE_USER ROLE_ADMIN")
    private String roles;

    /**
     * E-mail
     */
    @Schema(description = "E-mail", required = true, example = "shev-81@mail.ru")
    private String mail;

    @Schema(description = "User's Tel", required = true, example = "8(903)178-30-50")
    private String phone;

    @Schema(description = "User password", required = true, example = "*******")
    private String pass;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserDto setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public UserDto setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public UserDto setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getFio() {
        return fio;
    }

    public UserDto setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
