package webmarket.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель деталей в заказе")
public class OrderDetailsDto {

    @Schema(description = "Почтовый код", required = true, example = "143000")
    private String postalcode;

    @Schema(description = "Город", required = true, example = "г. Москва")
    private String adminAreaTwoTown;

    @Schema(description = "Улица", required = true, example = "ул. Лубянка")
    private String addressLineOneStreet;

    @Schema(description = "Номер апартаментов", required = true, example = "2/3")
    private String addressLineTwoApartmentNumber;

    @Schema(description = "E-mail", required = true, example = "ivan123@mail.ru")
    private String email;

    @Schema(description = "Телефон в заказе", required = true, example = "8(903)495-12-23")
    private String phone;

    public OrderDetailsDto() {
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getAdminAreaTwoTown() {
        return adminAreaTwoTown;
    }

    public void setAdminAreaTwoTown(String adminAreaTwoTown) {
        this.adminAreaTwoTown = adminAreaTwoTown;
    }

    public String getAddressLineOneStreet() {
        return addressLineOneStreet;
    }

    public void setAddressLineOneStreet(String addressLineOneStreet) {
        this.addressLineOneStreet = addressLineOneStreet;
    }

    public String getAddressLineTwoApartmentNumber() {
        return addressLineTwoApartmentNumber;
    }

    public void setAddressLineTwoApartmentNumber(String addressLineTwoApartmentNumber) {
        this.addressLineTwoApartmentNumber = addressLineTwoApartmentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
