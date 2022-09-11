package com.webmarket.repositories;

import com.webmarket.entities.Statistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозитарий для хранения статистики по самым востребованным товарам (добавляемым в корзину покупателями.)
 */
@Repository
public interface AnalitRepository extends CrudRepository<Statistic, Integer> {

    /**
     * Запрос получения всех продуктов.
     * @return список продуктов.
     */
    @Query("select s from Statistic s")
    List<Statistic> getAll ();

    /**
     * Запрос на получение статистики продукиа по имени продукта.
     * @param nameProduct
     * @return продукт по соответствию имени.
     */
    @Query("select s from Statistic s where s.nameProducts = :nameProduct")
    Optional<Statistic> findProductByName(String nameProduct);

    /**
     * Выбирает последние 10 записей продуктов отсортированные по востребованности.
     * @return
     */
    @Query(value = "select * from statistic order by count_visits desc limit 10", nativeQuery = true)
    List<Statistic> findLastTen();
}
