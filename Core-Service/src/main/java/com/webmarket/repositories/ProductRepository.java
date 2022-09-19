package com.webmarket.repositories;

import com.webmarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Продуктовый репозитарий.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * Выбор продукта по его имени.
     * @param name
     * @return
     */
    @Query("select p from Product p where p.name = :name")
    Product findByName(String name);
    }