package com.webmarket.repositories;

import com.webmarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Grocery repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Selecting a product by its name.
     * @param name
     * @return
     */
    @Query("select p from Product p where p.name = :name")
    Product findByName(String name);
    }