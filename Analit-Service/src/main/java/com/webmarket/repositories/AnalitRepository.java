package com.webmarket.repositories;

import com.webmarket.entities.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A repository for storing statistics on the most popular products (added to the cart by customers.)
 */
@Repository
public interface AnalitRepository extends CrudRepository<Statistic, Integer> {

    /**
     * Request to receive all products.
     * @return list of products.
     */
    @Query("select s from Statistic s")
    List<Statistic> getAll ();

    /**
     * Request to get product statistics by product name.
     * @param nameProduct
     * @return product by matching the name.
     */
    @Query("select s from Statistic s where s.nameProducts = :nameProduct")
    Optional<Statistic> findProductByName(String nameProduct);

    /**
     * Selects the last 10 product records sorted by demand.
     * @return List
     */
    @Query(value = "select * from statistic order by count_visits desc limit 10", nativeQuery = true)
    List<Statistic> findLastTen();
}
