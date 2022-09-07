package com.webmarket.controllers;

import com.webmarket.services.AnalitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webmarket.core.ProductDto;
import webmarket.analitic.StatisticDto;

import java.util.ArrayList;
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
        List<StatisticDto> listStatistic = analitService.getAll();
        return listStatistic;
    }


    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Запрос на сохранение списка товаров в аналитике.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    )
            }
    )
    public void saveStatistic (@RequestBody ArrayList<ProductDto> product){
        analitService.register(product);
    }



}
