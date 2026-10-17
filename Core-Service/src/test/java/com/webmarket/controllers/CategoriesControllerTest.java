package com.webmarket.controllers;

import com.webmarket.converters.CategoryConverter;
import com.webmarket.entities.Category;
import com.webmarket.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webmarket.core.CategoryDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for CategoriesController.
 */
@WebMvcTest(CategoriesController.class)
class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryConverter categoryConverter;

    // -----------------------------------------------------------------------
    // GET /api/v1/categories
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /categories — возвращается список из двух категорий
    void allCategories_whenCategoriesExist_shouldReturnList() throws Exception {
        Category cat1 = new Category();
        cat1.setName("Food");
        Category cat2 = new Category();
        cat2.setName("Electronics");

        List<Category> categories = Arrays.asList(cat1, cat2);
        List<CategoryDto> dtos = Arrays.asList(new CategoryDto("Food"), new CategoryDto("Electronics"));

        when(categoryService.getAllCategories()).thenReturn(categories);
        when(categoryConverter.entityToDto(categories)).thenReturn(dtos);

        mockMvc.perform(get("/api/v1/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Food"))
                .andExpect(jsonPath("$[1].name").value("Electronics"));
    }

    @Test
    // Сценарий: GET /categories — категорий нет, возвращается пустой список
    void allCategories_whenEmpty_shouldReturnEmptyList() throws Exception {
        List<Category> empty = Collections.emptyList();
        when(categoryService.getAllCategories()).thenReturn(empty);
        when(categoryConverter.entityToDto(empty)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}