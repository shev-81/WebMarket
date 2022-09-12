package com.webmarket.endpoints;

import com.webmarket.entities.Category;
import com.webmarket.services.CategoryService;
import com.webmarket.soap.categories.CategorySoap;
import com.webmarket.soap.categories.GetCategoryByTitleRequest;
import com.webmarket.soap.categories.GetCategoryByTitleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Энд поит Веб сервиса SOAP для получения категории по имени.
 */
@Endpoint
@RequiredArgsConstructor
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.shev.com/spring/ws/categories";
    private final CategoryService categoryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        CategorySoap categorySoap = new CategorySoap();
        Category category = categoryService.getCategoryByName(request.getName());
        categorySoap.setName(category.getName());
        response.setCategory(categorySoap);
        return response;
    }
}
