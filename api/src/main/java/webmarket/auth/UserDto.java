package webmarket.auth;

public class UserDto {
    private Long id;
    private String name;
    private String fio;
    private String roles;
    private String mail;
    private String phone;
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
