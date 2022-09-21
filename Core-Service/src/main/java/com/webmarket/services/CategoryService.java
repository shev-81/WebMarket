package com.webmarket.services;


import com.webmarket.entities.Category;
import com.webmarket.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with categories.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    /**
     * Returns a list of all categories.
     * @return
     */
    public List<Category> getAllCategories(){
        return categoriesRepository.getAll();
    }

    /**
     * Returns a category by its name.
     * @param nameCategory
     * @return
     */
    public Category getCategoryByName(String nameCategory){
        return categoriesRepository.findByName(nameCategory);
    }

    /**
     * Returns the Category Id by its name.
     * @param nameCategory
     * @return
     */
    public Integer getIdCategoryByName(String nameCategory){
        return categoriesRepository.findIdByName(nameCategory);
    }
}

