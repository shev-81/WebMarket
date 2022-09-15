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
 * Сервис продуктов рабоатет с продуктами магазина.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    /**
     * Продуктовый репозиторий.
     */
    private final ProductRepository productRepository;

    /**
     * Сервис категорий товаров.
     */
    private final CategoryService categoryService;

    /**
     * Возвращает старницу с продуктами.
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
     * Возвращает продукт из репозитария по его имени.
     * @param name
     * @return
     */
    public Product getByName (String name) {
        return productRepository.findByName(name);
    }

    /**
     * Возвращает список всех продуктов из репозитария.
     * @return
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Возвращает продукт по его Id.
     * @param id
     * @return
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Удаляет продукт по его ID.
     * @param id
     */
    public void delProdictById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Сохраняет продукт в Репозитарии.
     * @param product
     * @return
     */
    public Product save(Product product){
        return productRepository.save(product);
    }
}
