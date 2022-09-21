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

/**
 * Controller for working with products.
 */
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Methods of working with products")
public class ProductController {

    /**
     * Product service.
     */
    private final ProductService productService;

    /**
     * Converter to the essence of products and vice versa.
     */
    private final ProductConverter productConverter;

    /**
     * Product validator.
     */
    private final ProductValidator productValidator;

    /**
     * Request for a product page.
     * http://localhost:5555/core/api/v1/products
     * @param page
     * @param minPrice
     * @param maxPrice
     * @param nameCategory
     * @param namePart
     * @return
     */
    @GetMapping
    @Operation(
            summary = "Request to receive a product page.",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
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
     * Request to receive a product by id.
     * http://localhost:5555/core/api/v1/products/{id}
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Request to receive a product by id",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto getProductById(@PathVariable @Parameter(description = "Product ID", required = true) Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    /**
     * Request for a list of products.
     * http://localhost:5555/core/api/v1/products/all
     * @return
     */
    @GetMapping("/all")
    @Operation(
            summary = "Request for a list of products",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
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
     * Request to add a new product to the product database.
     * http://localhost:5555/core/api/v1/products
     * @param productDto
     * @return
     */
    @PostMapping
    @Operation(
            summary = "Request to add a new product to the product database",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto saveProduct(@RequestBody @Parameter(description = "Product DTO", required = true) ProductDto productDto) {
        productValidator.validate(productDto);
        productDto.setId(null);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }

    /**
     * Request to delete a product in the product database.
     * http://localhost:5555/core/api/v1/products/{id}
     * @param id
     */
    @Hidden
    @DeleteMapping("/{id}")
    public void delProducts(@PathVariable @Parameter(description = "Product ID", required = true) Long id) {
        productService.delProdictById(id);
    }

    /**
     * Request to save changes to the product in the product database.
     * http://localhost:5555/core/api/v1/products
     * @param productDto
     * @return
     */
    @PutMapping
    @Operation(
            summary = "Request to save changes to the product in the product database",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ProductDto updateProducts(@RequestBody @Parameter(description = "Product DTO", required = true)ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productService.save(product);
        return productConverter.entityToDto(product);
    }
}
