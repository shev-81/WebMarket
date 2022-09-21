package com.webmarket.repositories;

import com.webmarket.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of orders.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    /**
     * Selecting all orders by user name.
     * @param username
     * @return
     */
    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);
}
