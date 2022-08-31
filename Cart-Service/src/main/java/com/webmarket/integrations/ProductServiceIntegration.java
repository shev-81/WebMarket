package com.webmarket.integrations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import webmarket.core.ProductDto;

import java.util.Optional;

@Data
@Component
public class ProductServiceIntegration {

    @Autowired
    @Qualifier("coreServiceWebClient")
    private WebClient coreServiceWebClient;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

    public Optional <ProductDto> findById(Long id){
        ProductDto productDto = coreServiceWebClient.get()
                .uri(productServiceUrl + "/api/v1/products/" + id)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.of(productDto);
    }
}
