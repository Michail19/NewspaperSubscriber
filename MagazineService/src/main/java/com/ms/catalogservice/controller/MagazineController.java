package com.ms.catalogservice.controller;

import com.ms.catalogservice.dto.CatalogInputDTO;
import com.ms.catalogservice.dto.CatalogResponseDTO;
import com.ms.catalogservice.dto.CategoryInputDTO;
import com.ms.catalogservice.dto.SeriesInputDTO;
import com.ms.catalogservice.exception.CatalogNotFoundException;
import com.ms.catalogservice.model.Catalog;
import com.ms.catalogservice.model.Category;
import com.ms.catalogservice.model.Series;
import com.ms.catalogservice.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MagazineController {

    private final MagazineService magazineService;

    @Autowired
    public MagazineController(MagazineService magazineService) {
        this.magazineService = magazineService;
    }

    /* ---------- Catalog ---------- */

    @QueryMapping
    public List<Catalog> getCatalogs() {
        return magazineService.getAllCatalogs();
    }

    @QueryMapping
    public CatalogResponseDTO getCatalogById(@Argument Long id) {
        try {
            return magazineService.getCatalogById(id);
        }
        catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public Catalog addCatalog(@Argument("input") CatalogInputDTO input) {
        try {
            Catalog catalog = new Catalog();
            catalog.setTitle(input.getTitle());
            catalog.setDescription(input.getDescription());
            catalog.setPrice(input.getPrice());
            catalog.setLink(input.getLink());
            return magazineService.addCatalog(catalog, input.getCategoryId(), input.getSeriesId());
        }
        catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public Catalog updateCatalog(@Argument Long id, @Argument("input") CatalogInputDTO input) {
        try {
            Catalog updated = new Catalog();
            updated.setTitle(input.getTitle());
            updated.setDescription(input.getDescription());
            updated.setPrice(input.getPrice());
            updated.setLink(input.getLink());
            return magazineService.updateCatalog(id, updated, input.getCategoryId(), input.getSeriesId());
        }
        catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public boolean deleteCatalog(@Argument Long id) {
        return magazineService.deleteCatalog(id);
    }

    /* ---------- Category ---------- */

    @QueryMapping
    public List<Category> getCategories() {
        return magazineService.getAllCategories();
    }

    @MutationMapping
    public Category addCategory(@Argument("input") CategoryInputDTO input) {
        return magazineService.addCategory(input.getName());
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument("input") CategoryInputDTO input) {
        return magazineService.updateCategory(id, input.getName());
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return magazineService.deleteCategory(id);
    }

    /* ---------- Series ---------- */

    @QueryMapping
    public List<Series> getSeries() {
        return magazineService.getAllSeries();
    }

    @MutationMapping
    public Series addSeries(@Argument("input") SeriesInputDTO input) {
        return magazineService.addSeries(input.getName());
    }

    @MutationMapping
    public Series updateSeries(@Argument Long id, @Argument("input") SeriesInputDTO input) {
        return magazineService.updateSeries(id, input.getName());
    }

    @MutationMapping
    public Boolean deleteSeries(@Argument Long id) {
        return magazineService.deleteSeries(id);
    }
}
