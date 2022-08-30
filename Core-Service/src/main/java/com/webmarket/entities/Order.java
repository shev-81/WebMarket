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


@Entity
@Data
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "postalcode")
    String postalCode;

    @Column(name = "adminarea2_town")
    String adminAreaTwoTown;

    @Column(name = "addressline1_street")
    String addressLineOneStreet;

    @Column(name = "addressline2_apartment_n")
    String addressLineTwoApartmentNumber;

    @Column(name = "fio")
    String fio;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

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
