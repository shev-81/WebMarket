package com.webmarket.repositories;

import com.webmarket.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозитарий категорий.
 */
@Repository
public interface CategoriesRepository extends CrudRepository <Category, Long> {

    /**
     * Выбор всех категорий.
     * @return
     */
    @Query("select c from Category c")
    List<Category> getAll();

    /**
     * Выбор Id категории по ее имени.
     * @param nameCategory
     * @return
     */
    @Query("select c.id from Category c WHERE c.name = :nameCategory")
    Integer findIdByName(String nameCategory);

    /**
     * Выбор Категории по ее имени.
     * @param nameCategory
     * @return
     */
    @Query("select c from Category c WHERE c.name = :nameCategory")
    Category findByName(String nameCategory);
}
