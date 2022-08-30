package com.webmarket.converters;

import com.webmarket.entities.Category;
import org.springframework.stereotype.Component;
import webmarket.core.CategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryConverter {

    public List<CategoryDto> entityToDto(List<Category> categories){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category: categories){
            categoryDtoList.add(new CategoryDto(category.getName()));
        }
        return categoryDtoList;
    }

    public Category dtoToEntity(CategoryDto categoryDto) {
        throw new UnsupportedOperationException();
    }
}
