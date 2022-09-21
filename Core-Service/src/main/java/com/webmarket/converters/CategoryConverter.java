package com.webmarket.converters;

import com.webmarket.entities.Category;
import org.springframework.stereotype.Component;
import webmarket.core.CategoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity converter to DTO and back.
 */
@Component
public class CategoryConverter {

    /**
     * Converts a list of categories to a list of DTO categories.
     * @param categories
     * @return List
     */
    public List<CategoryDto> entityToDto(List<Category> categories){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category: categories){
            categoryDtoList.add(new CategoryDto(category.getName()));
        }
        return categoryDtoList;
    }

    /**
     * Temporarily not used and throws.
     * @see UnsupportedOperationException
     * @param categoryDto
     * @return Category
     */
    public Category dtoToEntity(CategoryDto categoryDto) {
        throw new UnsupportedOperationException();
    }
}
