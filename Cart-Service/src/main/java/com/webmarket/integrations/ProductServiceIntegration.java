package com.webmarket.integrations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import webmarket.core.ProductDto;

import java.util.Optional;

/**
 * Responsible for integration with the Core service.
 */
@Data
@Component
public class ProductServiceIntegration {

    /**
     * Feign Client Product Service.
     */
    private final ClientFeignProductService productService;

    /**
     * Sends a request to the Core service requests the product by its Id.
     * @param id
     * @return
     */
    public Optional <ProductDto> findById(Long id){
        ProductDto productDto = productService.findById(id);
        return Optional.of(productDto);
    }
}
