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
import webmarket.analitic.StatisticDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The service registers in the repository the products that the user adds.
 * If the product type is already in the repository, then its statistical quantity simply increases.
 * The service polls the basket every 10 seconds and collects data from it.
 * @see AnalitRepository
 * @see RestTemplate
 */
@Service
@RequiredArgsConstructor
public class AnalitService {

    /**
     * The address of the bucket service.
     */
    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;

    /**
     * Statistics repository.
     */
    private final AnalitRepository analitRepository;

    /**
     * A list of products from the 10 positions most in demand by customers.
     */
    private List<StatisticDto> listProduct;

    @PostConstruct
    private void init(){
        listProduct = new ArrayList<>();
    }

    public List<StatisticDto> getData(){
        return listProduct;
    }

    /**
     * Registers the list of products received from the shopping cart service in the database.
     * @param product
     */
    @Transactional
    public void register(ProductDto product){
        Statistic statistic = analitRepository.findProductByName(product.getName()).orElse(null);
        if(statistic == null){
            statistic = new Statistic(product.getName(), 1);
        }else{
            statistic.setCountVisits(statistic.getCountVisits()+1);
        }
        analitRepository.save(statistic);
    }

    /**
     * Updates the data in the list of the most popular products every 10 seconds. The presence of a request, in fact, ping is perceived by the service
     * baskets as a team to form a response and clear your temporary list of demanded goods.
     */
    @Scheduled(fixedDelay = 10000)
    public void requestData(){
        listProduct = analitRepository.findLastTen().stream().map(s-> (new StatisticDto(s.getNameProducts(), s.getCountVisits()))).collect(Collectors.toList());
    }
}
