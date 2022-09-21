package com.webmarket.integrations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import webmarket.core.ProductDto;

import java.util.ArrayList;

/**
 * Responsible for integration with the Analytics service.
 */
@Data
@Component
public class AnalitServiceIntegration {

    /**
     * Client for requests to the analytics service.
     */
    @Autowired
    @Qualifier("analyticServiceWebClient")
    private WebClient analitServiceWebClient;

    /**
     * Sends a request to the analytics service for product registration.
     * @param productDto
     */
    public void registration(ArrayList <ProductDto> productDto){
        analitServiceWebClient.post()
                .uri("/api/v1/analit/reg")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productDto))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
