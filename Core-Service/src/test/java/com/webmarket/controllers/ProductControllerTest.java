package com.webmarket.controllers;

import com.webmarket.converters.ProductConverter;
import com.webmarket.entities.Product;
import com.webmarket.exceptions.ValidationException;
import com.webmarket.services.ProductService;
import com.webmarket.validators.ProductValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webmarket.core.ProductDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ProductController.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductConverter productConverter;

    @MockBean
    private ProductValidator productValidator;

    // -----------------------------------------------------------------------
    // GET /api/v1/products – paginated list with filters
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /products с page=1 — возвращается страница с одним продуктом
    void getAllProducts_whenPageIsValid_shouldReturnPage() throws Exception {
        Product product = new Product(1L, "Apple", BigDecimal.valueOf(50));
        ProductDto dto = new ProductDto(1L, "Apple", BigDecimal.valueOf(50));
        Page<Product> page = new PageImpl<>(Collections.singletonList(product));

        when(productService.findAll(any(), any(), any(), any(), eq(1))).thenReturn(page);
        when(productConverter.entityToDto(product)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/products")
                        .param("p", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Apple"))
                .andExpect(jsonPath("$.content[0].price").value(50));
    }

    @Test
    // Сценарий: GET /products с min_price, max_price, name_part, name_category — фильтры передаются в сервис
    void getAllProducts_withFilters_shouldPassFilters() throws Exception {
        Page<Product> emptyPage = new PageImpl<>(Collections.emptyList());
        when(productService.findAll(eq(10), eq(100), eq("apple"), eq("food"), eq(2))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/products")
                        .param("p", "2")
                        .param("min_price", "10")
                        .param("max_price", "100")
                        .param("name_part", "apple")
                        .param("name_category", "food")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    // Сценарий: GET /products с page=0 — page принудительно становится 1
    void getAllProducts_whenPageLessThanOne_shouldDefaultToPageOne() throws Exception {
        Page<Product> emptyPage = new PageImpl<>(Collections.emptyList());
        when(productService.findAll(any(), any(), any(), any(), eq(1))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/products")
                        .param("p", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/products/{id} – get by id
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /products/{id} — продукт найден, возвращается 200 с DTO
    void getProductById_whenExists_shouldReturnProduct() throws Exception {
        Product product = new Product(1L, "Apple", BigDecimal.valueOf(50));
        ProductDto dto = new ProductDto(1L, "Apple", BigDecimal.valueOf(50));

        when(productService.findById(1L)).thenReturn(Optional.of(product));
        when(productConverter.entityToDto(product)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/products/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Apple"));
    }

    @Test
    // Сценарий: GET /products/{id} — продукт не найден, возвращается 404 с сообщением
    void getProductById_whenNotFound_shouldReturn404() throws Exception {
        when(productService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Product not found, id: 999"));
    }

    // -----------------------------------------------------------------------
    // GET /api/v1/products/all – full list
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: GET /products/all — возвращается полный список продуктов
    void getAllProductsList_shouldReturnList() throws Exception {
        Product product1 = new Product(1L, "Apple", BigDecimal.valueOf(50));
        Product product2 = new Product(2L, "Banana", BigDecimal.valueOf(30));
        ProductDto dto1 = new ProductDto(1L, "Apple", BigDecimal.valueOf(50));
        ProductDto dto2 = new ProductDto(2L, "Banana", BigDecimal.valueOf(30));

        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productConverter.entityToDto(product1)).thenReturn(dto1);
        when(productConverter.entityToDto(product2)).thenReturn(dto2);

        mockMvc.perform(get("/api/v1/products/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].name").value("Banana"));
    }

    // -----------------------------------------------------------------------
    // POST /api/v1/products – create
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: POST /products с валидными данными — продукт создаётся, возвращается 200
    void saveProduct_withValidData_shouldCreate() throws Exception {
        ProductDto input = new ProductDto(null, "New Product", BigDecimal.valueOf(100));
        Product entity = new Product(null, "New Product", BigDecimal.valueOf(100));
        Product saved = new Product(1L, "New Product", BigDecimal.valueOf(100));
        ProductDto output = new ProductDto(1L, "New Product", BigDecimal.valueOf(100));

        doNothing().when(productValidator).validate(any(ProductDto.class));
        when(productConverter.dtoToEntity(any(ProductDto.class))).thenReturn(entity);
        when(productService.save(entity)).thenReturn(saved);
        when(productConverter.entityToDto(saved)).thenReturn(output);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Product\", \"price\": 100}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    // Сценарий: POST /products с ценой <1 — ValidationException -> 400
    void saveProduct_withInvalidPrice_shouldReturn400() throws Exception {
        doThrow(new ValidationException(Arrays.asList("Цена продукта не может быть меньше 1")))
                .when(productValidator).validate(any(ProductDto.class));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Cheap\", \"price\": 0}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorFieldsMessages[0]").value("Цена продукта не может быть меньше 1"));
    }

    // -----------------------------------------------------------------------
    // PUT /api/v1/products – update
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: PUT /products с валидными данными — продукт обновляется
    void updateProduct_withValidData_shouldUpdate() throws Exception {
        ProductDto input = new ProductDto(1L, "Updated", BigDecimal.valueOf(150));
        Product entity = new Product(1L, "Updated", BigDecimal.valueOf(150));
        Product saved = new Product(1L, "Updated", BigDecimal.valueOf(150));
        ProductDto output = new ProductDto(1L, "Updated", BigDecimal.valueOf(150));

        doNothing().when(productValidator).validate(any(ProductDto.class));
        when(productConverter.dtoToEntity(any(ProductDto.class))).thenReturn(entity);
        when(productService.save(entity)).thenReturn(saved);
        when(productConverter.entityToDto(saved)).thenReturn(output);

        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated\", \"price\": 150}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    // Сценарий: PUT /products с пустым названием — ValidationException -> 400
    void updateProduct_withBlankName_shouldReturn400() throws Exception {
        doThrow(new ValidationException(Arrays.asList("Продукт не может иметь пустое название")))
                .when(productValidator).validate(any(ProductDto.class));

        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"\", \"price\": 50}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorFieldsMessages[0]").value("Продукт не может иметь пустое название"));
    }

    // -----------------------------------------------------------------------
    // DELETE /api/v1/products/{id}
    // -----------------------------------------------------------------------

    @Test
    // Сценарий: DELETE /products/{id} — продукт удаляется, сервис вызывается 1 раз
    void deleteProduct_shouldDelete() throws Exception {
        doNothing().when(productService).delProdictById(1L);

        mockMvc.perform(delete("/api/v1/products/{id}", 1L))
                .andExpect(status().isOk());

        verify(productService, times(1)).delProdictById(1L);
    }
}