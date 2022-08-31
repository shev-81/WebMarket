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

@Data
@Component
public class AnalitServiceIntegration {

    @Autowired
    @Qualifier("analyticServiceWebClient")
    private WebClient analitServiceWebClient;

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
