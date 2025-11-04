package com.ms.catalogservice.controller;

import com.ms.catalogservice.dto.CatalogInputDTO;
import com.ms.catalogservice.model.Catalog;
import com.ms.catalogservice.model.Category;
import com.ms.catalogservice.model.Series;
import com.ms.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @QueryMapping
    public List<Catalog> getCatalogs() {
        return catalogService.getAllCatalogs();
    }

    @QueryMapping
    public Catalog getCatalogById(@Argument Long id) {
        return catalogService.getCatalogById(id);
    }

    @QueryMapping
    public List<Category> getCategories() {
        return catalogService.getAllCategories();
    }

    @QueryMapping
    public List<Series> getSeries() {
        return catalogService.getAllSeries();
    }

    @MutationMapping
    public Catalog addCatalog(@Argument CatalogInputDTO input) {
        Catalog catalog = new Catalog();
        catalog.setTitle(input.getTitle());
        catalog.setDescription(input.getDescription());
        catalog.setPrice(input.getPrice());
        catalog.setLink(input.getLink());

        return catalogService.addCatalog(catalog, input.getCategoryId(), input.getSeriesId());
    }

    @MutationMapping
    public Catalog updateCatalog(@Argument Long id, @Argument CatalogInputDTO input) {
        Catalog updated = new Catalog();
        updated.setTitle(input.getTitle());
        updated.setDescription(input.getDescription());
        updated.setPrice(input.getPrice());
        updated.setLink(input.getLink());

        return catalogService.updateCatalog(id, updated, input.getCategoryId(), input.getSeriesId());
    }

    @MutationMapping
    public boolean deleteCatalog(@Argument Long id) {
        return catalogService.deleteCatalog(id);
    }
}
