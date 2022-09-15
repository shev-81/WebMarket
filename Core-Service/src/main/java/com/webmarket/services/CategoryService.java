package com.webmarket.services;


import com.webmarket.entities.Category;
import com.webmarket.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис работы с категориями.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    /**
     * Возвращает Список всех категорий
     * @return
     */
    public List<Category> getAllCategories(){
        return categoriesRepository.getAll();
    }

    /**
     * Возвращает категорию по ее имени.
     * @param nameCategory
     * @return
     */
    public Category getCategoryByName(String nameCategory){
        return categoriesRepository.findByName(nameCategory);
    }

    /**
     * Возвращает Id категории по ее имени.
     * @param nameCategory
     * @return
     */
    public Integer getIdCategoryByName(String nameCategory){
        return categoriesRepository.findIdByName(nameCategory);
    }
}

