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
 * Сервис регистрирует в репозитарии продукты которые добавляет пользователь.
 * Если тип продукта уже есть в репозитарии то просто увеличивается его статистическое колличество.
 * Сервис каждые 10 секунд опрашивает корзину и собирает из нее данные.
 * @see AnalitRepository
 * @see RestTemplate
 */
@Service
@RequiredArgsConstructor
public class AnalitService {

    /**
     * Адресс сервиса корзины.
     */
    @Value("${integrations.cart-service.url}")
    private String cartServiceUrl;

    /**
     * Репозитарий статистики.
     */
    private final AnalitRepository analitRepository;

    /**
     * Список продуктов из 10 позицый самых востребованных покупателями.
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
     * Регистрирует список продуктов, полученный от сервиса корзины, в базе данных.
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
     * Каждые 10 секунд обновляет данные в списке самых востребованных товаров. Наличие запроса, по сути пинга воспринимается сервисом
     * корзины как команда для формирования ответа и очищения своего временного списка востребованных товаров.
     */
    @Scheduled(fixedDelay = 10000)
    public void requestData(){
        listProduct = analitRepository.findLastTen().stream().map(s-> (new StatisticDto(s.getNameProducts(), s.getCountVisits()))).collect(Collectors.toList());
    }
}
