package com.webmarket.repositories;

import com.webmarket.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends CrudRepository<OrderItem, Long> {
}
