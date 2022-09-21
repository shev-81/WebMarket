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
 * The essence of orders.
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
     * Username.
     */
    @Column(name = "username")
    private String username;

    /**
     * LastName
     */
    @Column(name = "fio")
    String fio;

    /**
     * The full price of the order.
     */
    @Column(name = "total_price")
    BigDecimal totalPrice;

    /**
     * Postal code.
     */
    @Column(name = "postalcode")
    String postalCode;

    /**
     * Address district.
     */
    @Column(name = "adminarea2_town")
    String adminAreaTwoTown;

    /**
     * Address street.
     */
    @Column(name = "addressline1_street")
    String addressLineOneStreet;

    /**
     * Flat
     */
    @Column(name = "addressline2_apartment_n")
    String addressLineTwoApartmentNumber;

    /**
     * Telephone.
     */
    @Column(name = "phone")
    String phone;

    /**
     * email.
     */
    @Column(name = "email")
    String email;

    /**
     * The order status is determined by the payment.
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
