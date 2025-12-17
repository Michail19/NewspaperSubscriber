package com.ms.apigateway.controller;

import com.ms.apigateway.service.MagazineClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MagazineController {

    private final MagazineClient magazineClient;

    public MagazineController(MagazineClient magazineClient) {
        this.magazineClient = magazineClient;
    }

    // Каталог
    @QueryMapping
    public Mono<Object> getCatalogs() {
        return magazineClient.getCatalogs();
    }

    @QueryMapping
    public Mono<Object> getCatalogById(@Argument String id) {
        return magazineClient.getCatalogById(id);
    }

    @MutationMapping
    public Mono<Object> addCatalog(@Argument("input") Object input) {
        return magazineClient.addCatalog(input);
    }

    @MutationMapping
    public Mono<Object> updateCatalog(@Argument String id, @Argument("input") Object input) {
        return magazineClient.updateCatalog(id, input);
    }

    @MutationMapping
    public Mono<Object> deleteCatalog(@Argument String id) {
        return magazineClient.deleteCatalog(id);
    }

    // Категории
    @QueryMapping
    public Mono<Object> getCategories() {
        return magazineClient.getCategories();
    }

    @QueryMapping
    public Mono<Object> getCategoryById(@Argument String id) {
        return magazineClient.getCategoryById(id);
    }

    @MutationMapping
    public Mono<Object> addCategory(@Argument("input") Object input) {
        return magazineClient.addCategory(input);
    }

    @MutationMapping
    public Mono<Object> updateCategory(@Argument String id, @Argument("input") Object input) {
        return magazineClient.updateCategory(id, input);
    }

    @MutationMapping
    public Object deleteCategory(@Argument String id) {
        return magazineClient.deleteCategory(id);
    }

    // Серии
    @QueryMapping
    public Object getSeries() {
        return magazineClient.getSeries();
    }

    @QueryMapping
    public Object getSeriesById(@Argument String id) {
        return magazineClient.getSeriesById(id);
    }

    @MutationMapping
    public Object addSeries(@Argument("input") Object input) {
        return magazineClient.addSeries(input);
    }

    @MutationMapping
    public Object updateSeries(@Argument String id, @Argument("input") Object input) {
        return magazineClient.updateSeries(id, input);
    }

    @MutationMapping
    public Object deleteSeries(@Argument String id) {
        return magazineClient.deleteSeries(id);
    }
}
