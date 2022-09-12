package com.webmarket.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.webmarket.enums.StatusCode.GENERATED;

/**
 * Сущность заказов.
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "username")
    private String username;

    /**
     * Полная цена заказа.
     */
    @Column(name = "total_price")
    BigDecimal totalPrice;

    /**
     * Почтовый код.
     */
    @Column(name = "postalcode")
    String postalCode;

    /**
     * Адресс район.
     */
    @Column(name = "adminarea2_town")
    String adminAreaTwoTown;

    /**
     * Адресс улица.
     */
    @Column(name = "addressline1_street")
    String addressLineOneStreet;

    /**
     * Квартира
     */
    @Column(name = "addressline2_apartment_n")
    String addressLineTwoApartmentNumber;

    /**
     * ФИО
     */
    @Column(name = "fio")
    String fio;

    /**
     * Телефон.
     */
    @Column(name = "phone")
    String phone;

    /**
     * Емайл.
     */
    @Column(name = "email")
    String email;

    /**
     * Статус заказа определяется оплатой.
     */
    @Column(name = "status")
    String status;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItem> items;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Order() {
        status = GENERATED.name();
    }
}
