package com.webmarket.converters;

import com.webmarket.entities.Category;
import org.springframework.stereotype.Component;
import webmarket.core.CategoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер сущности в ДТО и обратно.
 */
@Component
public class CategoryConverter {

    /**
     * Конвертирует список категорий в список ДТО категорий.
     * @param categories
     * @return
     */
    public List<CategoryDto> entityToDto(List<Category> categories){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category: categories){
            categoryDtoList.add(new CategoryDto(category.getName()));
        }
        return categoryDtoList;
    }

    /**
     * Временно не используется и кидает.
     * @see UnsupportedOperationException
     * @param categoryDto
     * @return
     */
    public Category dtoToEntity(CategoryDto categoryDto) {
        throw new UnsupportedOperationException();
    }
}
