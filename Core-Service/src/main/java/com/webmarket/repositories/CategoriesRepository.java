package com.webmarket.repositories;

import com.webmarket.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Category repository.
 */
@Repository
public interface CategoriesRepository extends CrudRepository <Category, Long> {

    /**
     * Selection of all categories.
     * @return List
     */
    @Query("select c from Category c")
    List<Category> getAll();

    /**
     * Selecting the Category Id by its name.
     * @param nameCategory
     * @return Integer
     */
    @Query("select c.id from Category c WHERE c.name = :nameCategory")
    Integer findIdByName(String nameCategory);

    /**
     * Selecting a Category by its name.
     * @param nameCategory
     * @return Category
     */
    @Query("select c from Category c WHERE c.name = :nameCategory")
    Category findByName(String nameCategory);
}
