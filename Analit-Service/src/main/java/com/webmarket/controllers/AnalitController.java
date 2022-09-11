package com.webmarket.controllers;

import com.webmarket.services.AnalitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import webmarket.core.ProductDto;
import webmarket.analitic.StatisticDto;

import java.util.List;

/**
 * Контроллер Микросервиса доступен по адресу http://localhost:8111/web-market-analit/api/v1/analit
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Аналитика", description = "Методы работы с аналитическими данными")
@RequestMapping("/api/v1/analit")
public class AnalitController {

    /**
     * Сервис Аналитики.
     */
    private final AnalitService analitService;

    @GetMapping
    @Operation(
            summary = "Запрос на получение списка товаров в аналитике",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = StatisticDto.class))
                            )
                    )
            }
    )
    public List<StatisticDto> allData(){
        List<StatisticDto> listStatistic = analitService.getData();
        return listStatistic;
    }

    /**
     * Слушатель забирает из очереди пришедший объект продукта и сохраняет его
     * через сервис аналитики в своем репозитарии.
     * @param product
     */
    @KafkaListener(topics = "ProductAnalit")
    public void msgListener(ProductDto product){
        analitService.register(product);
    }
}
