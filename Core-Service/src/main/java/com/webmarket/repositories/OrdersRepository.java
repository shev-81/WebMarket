package com.webmarket.repositories;

import com.webmarket.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозитарий заказов
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    /**
     * Выбор всех заказов по имени пользователя.
     * @param username
     * @return
     */
    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);
}
