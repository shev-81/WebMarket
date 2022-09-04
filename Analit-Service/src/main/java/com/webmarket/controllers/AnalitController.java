package com.webmarket.controllers;

import com.webmarket.services.AnalitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webmarket.core.ProductDto;
import webmarket.analitic.StatisticDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/analit")
public class AnalitController {

    private final AnalitService analitService;

    @GetMapping
    public List<StatisticDto> allData(){
        List<StatisticDto> listStatistic = analitService.getAll();
        return listStatistic;
    }

    @PostMapping("/reg")
    public void saveStatistic (@RequestBody ArrayList<ProductDto> product){
        analitService.register(product);
    }



}
