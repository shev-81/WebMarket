package com.webmarket.controllers;

import com.webmarket.converters.ProductConverter;
import com.webmarket.entities.Product;
import com.webmarket.services.ProductService;
import com.webmarket.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import webmarket.core.ProductDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    /**
     * Запрос на получение страницы продуктов
     * @param page
     * @param minPrice
     * @param maxPrice
     * @param nameCategory
     * @param namePart
     * @return
     */
    @GetMapping
    @Operation(
            summary = "Запрос на получение страницы продуктов.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "name_category", required = false) String nameCategory,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(minPrice, maxPrice, namePart, nameCategory, page).map(p -> productConverter.entityToDto(p)
        );
    }

    /**
     * Запрос на получение продукта по id.
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto getProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    /**
     * Запрос на получение списка продуктов.
     * @return
     */
    @GetMapping("/all")
    @Operation(
            summary = "Запрос на получение списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))                            )
                    )
            }
    )
    public List<ProductDto> getAll() {
        return productService.findAll().stream().map(p -> productConverter.entityToDto(p)).collect(Collectors.toList());
    }

    /**
     * Запрос на добавление нового продукта в БД продуктов.
     * @param productDto
     * @return
     */
    @PostMapping
    @Operation(
            summary = "Запрос на добавление нового продукта в БД продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto saveProduct(@RequestBody @Parameter(description = "DTO продукта", required = true) ProductDto productDto) {
        productValidator.validate(productDto);
        productDto.setId(null);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    /**
     * Запрос на удаление продукта в БД продуктов.
     * @param id
     */
    @Hidden
    @DeleteMapping("/{id}")
    public void delProducts(@PathVariable @Parameter(description = "ID продукта", required = true) Long id) {
        productService.delProdictById(id);
    }

    /**
     * Запрос на сохранение изменений у продукта в БД продуктов.
     * @param productDto
     * @return
     */
    @PutMapping
    @Operation(
            summary = "Запрос на сохранение изменений у продукта в БД продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto updateProducts(@RequestBody @Parameter(description = "DTO продукта", required = true)ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }
}
