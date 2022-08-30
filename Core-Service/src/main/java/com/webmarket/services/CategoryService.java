package com.webmarket.services;


import com.webmarket.entities.Category;
import com.webmarket.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    public List<Category> getAllCategories(){
        return categoriesRepository.getAll();
    }

    public Category getCategoryByName(String nameCategory){
        return categoriesRepository.findByName(nameCategory);
    }

    public Integer getIdCategoryByName(String nameCategory){
        return categoriesRepository.findIdByName(nameCategory);
    }
}

