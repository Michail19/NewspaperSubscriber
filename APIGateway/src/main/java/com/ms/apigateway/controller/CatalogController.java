package com.ms.apigateway.controller;

import com.ms.apigateway.service.CatalogClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CatalogController {

    private final CatalogClient catalogClient;

    public CatalogController(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    @QueryMapping
    public Object getCatalogById(String id) {
        return catalogClient.getCatalogById(id);
    }

    @MutationMapping
    public Object addCatalog(@Argument("input") Object input) {
        return catalogClient.addCatalog(input);
    }
}
