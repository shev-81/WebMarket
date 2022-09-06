package webmarket.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO пользователя.
 */
@Schema(description = "Модель DTO пользователя.")
public class UserDto {

    @Schema(description = "ID пользователя", required = true, example = "3")
    private Long id;

    @Schema(description = "Имя пользователя", required = true, example = "Иван")
    private String name;

    @Schema(description = "Фамилия пользователя", required = true, example = "Иванов")
    private String fio;

    @Schema(description = "Роли пользователя", required = true, example = "ROLE_USER ROLE_ADMIN")
    private String roles;

    @Schema(description = "E-mail пользователя", required = true, example = "shev-81@mail.ru")
    private String mail;

    @Schema(description = "Tel пользователя", required = true, example = "8(903)178-30-50")
    private String phone;

    @Schema(description = "Пароль пользователя", required = true, example = "*******")
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
