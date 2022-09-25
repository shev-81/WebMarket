package com.webmarket.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webmarket.core.ProductDto;

@FeignClient(value = "productService", url = "${integrations.core-service.url}")
public interface ClientFeignProductService {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{id}")
    ProductDto findById(@PathVariable("id") Long id);
}