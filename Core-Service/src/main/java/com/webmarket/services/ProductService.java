package com.webmarket.services;

import com.webmarket.entities.Product;
import com.webmarket.repositories.ProductRepository;
import com.webmarket.repositories.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The product service works with the store's products.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    /**
     * Grocery repository.
     */
    private final ProductRepository productRepository;

    /**
     * Service of product categories.
     */
    private final CategoryService categoryService;

    /**
     * Returns a page with products.
     * @param minScore
     * @param maxScore
     * @param partName
     * @param nameCategory
     * @param page
     * @return
     */
    public Page<Product> findAll(Integer minScore, Integer maxScore, String partName, String nameCategory, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minScore != null) {
            spec = spec.and(ProductSpecifications.scoreGreaterOrEqualsThan(minScore));
        }
        if (maxScore != null) {
            spec = spec.and(ProductSpecifications.scoreLessThanOrEqualsThan(maxScore));
        }
        if (partName != null) {
            spec = spec.and(ProductSpecifications.nameLike(partName));
        }
        if (nameCategory != null) {
            Integer idCategory = categoryService.getIdCategoryByName(nameCategory);
            spec = spec.and(ProductSpecifications.categoryLike(idCategory));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    /**
     * Returns a product from the repository by its name.
     * @param name
     * @return
     */
    public Product getByName (String name) {
        return productRepository.findByName(name);
    }

    /**
     * Returns a list of all products from the repository.
     * @return
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Returns the product by its Id.
     * @param id
     * @return
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Deletes a product by its ID.
     * @param id
     */
    public void delProdictById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Saves the product in the Repository.
     * @param product
     * @return
     */
    public Product save(Product product){
        return productRepository.save(product);
    }
}
