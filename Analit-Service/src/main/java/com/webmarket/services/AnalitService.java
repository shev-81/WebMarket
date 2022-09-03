package com.webmarket.services;


import com.webmarket.entities.Statistic;
import com.webmarket.repositories.AnalitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import webmarket.core.ProductDto;
import webmarket.statistics.StatisticDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalitService {

    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;

    private final AnalitRepository analitRepository;

    private final RestTemplate restTemplate;

    public List<StatisticDto> getAll(){
        return analitRepository.getAll().stream().map(s-> (new StatisticDto(s.getNameProducts(), s.getCountVisits()))).collect(Collectors.toList());
    }

    @Transactional
    public void register(ArrayList<ProductDto> productDtoList){
        for(ProductDto product: productDtoList){
            Statistic statistic = analitRepository.findProductByName(product.getName()).orElse(null);
            if(statistic == null){
                statistic = new Statistic(product.getName(), 1);
            }else{
                statistic.setCountVisits(statistic.getCountVisits()+1);
            }
            analitRepository.save(statistic);
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void requestData(){
        restTemplate.getForObject(cartServiceUrl + "/api/v1/cart/analit", Void.class);
    }
}
