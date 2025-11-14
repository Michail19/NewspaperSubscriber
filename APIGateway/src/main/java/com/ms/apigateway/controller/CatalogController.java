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

    // Каталог
    @QueryMapping
    public Object getCatalogs() {
        return catalogClient.getCatalogs();
    }

    @QueryMapping
    public Object getCatalogById(@Argument String id) {
        return catalogClient.getCatalogById(id);
    }

    @MutationMapping
    public Object addCatalog(@Argument("input") Object input) {
        return catalogClient.addCatalog(input);
    }

    @MutationMapping
    public Object updateCatalog(@Argument String id, @Argument("input") Object input) {
        return catalogClient.updateCatalog(id, input);
    }

    @MutationMapping
    public Object deleteCatalog(@Argument String id) {
        return catalogClient.deleteCatalog(id);
    }

    // Категории
    @QueryMapping
    public Object getCategories() {
        return catalogClient.getCategories();
    }

    @QueryMapping
    public Object getCategoryById(@Argument String id) {
        return catalogClient.getCategoryById(id);
    }

    @MutationMapping
    public Object addCategory(@Argument("input") Object input) {
        return catalogClient.addCategory(input);
    }

    @MutationMapping
    public Object updateCategory(@Argument String id, @Argument("input") Object input) {
        return catalogClient.updateCategory(id, input);
    }

    @MutationMapping
    public Object deleteCategory(@Argument String id) {
        return catalogClient.deleteCategory(id);
    }

    // Серии
    @QueryMapping
    public Object getSeries() {
        return catalogClient.getSeries();
    }

    @QueryMapping
    public Object getSeriesById(@Argument String id) {
        return catalogClient.getSeriesById(id);
    }

    @MutationMapping
    public Object addSeries(@Argument("input") Object input) {
        return catalogClient.addSeries(input);
    }

    @MutationMapping
    public Object updateSeries(@Argument String id, @Argument("input") Object input) {
        return catalogClient.updateSeries(id, input);
    }

    @MutationMapping
    public Object deleteSeries(@Argument String id) {
        return catalogClient.deleteSeries(id);
    }
}
