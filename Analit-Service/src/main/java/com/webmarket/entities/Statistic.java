package com.webmarket.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The Statistics entity stores the name of the product and the number of its additions by users to the cart.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "statistic")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_products")
    private String nameProducts;

    @Column(name = "count_visits")
    private Integer countVisits;

    public Statistic(String nameProducts, Integer countVisits) {
        this.nameProducts = nameProducts;
        this.countVisits = countVisits;
    }
}
