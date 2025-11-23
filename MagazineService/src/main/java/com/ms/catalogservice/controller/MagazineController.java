package com.ms.catalogservice.controller;

import com.ms.catalogservice.dto.*;
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
    public List<CatalogResponseDTO> getCatalogs() {
        return magazineService.getAllCatalogs()
                .stream()
                .map(magazineService::toCatalogDTO)
                .toList();
    }

    @QueryMapping
    public CatalogResponseDTO getCatalogById(@Argument Long id) {
        try {
            return magazineService.toCatalogDTO(magazineService.getCatalogById(id));
        } catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public CatalogResponseDTO addCatalog(@Argument("input") CatalogInputDTO input) {
        try {
            Catalog catalog = new Catalog();
            catalog.setTitle(input.getTitle());
            catalog.setDescription(input.getDescription());
            catalog.setPrice(input.getPrice());
            catalog.setLink(input.getLink());

            Catalog saved = magazineService.addCatalog(catalog, input.getCategoryId(), input.getSeriesId());
            return magazineService.toCatalogDTO(saved);
        } catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public CatalogResponseDTO updateCatalog(@Argument Long id, @Argument("input") CatalogInputDTO input) {
        try {
            Catalog updated = new Catalog();
            updated.setTitle(input.getTitle());
            updated.setDescription(input.getDescription());
            updated.setPrice(input.getPrice());
            updated.setLink(input.getLink());

            Catalog saved = magazineService.updateCatalog(id, updated, input.getCategoryId(), input.getSeriesId());
            return magazineService.toCatalogDTO(saved);
        } catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public boolean deleteCatalog(@Argument Long id) {
        return magazineService.deleteCatalog(id);
    }

    /* ---------- Category ---------- */

    @QueryMapping
    public List<CategoryResponseDTO> getCategories() {
        return magazineService.getAllCategories()
                .stream()
                .map(magazineService::toCategoryDTO)
                .toList();
    }

    @QueryMapping
    public CategoryResponseDTO getCategoryById(@Argument Long id) {
        try {
            return magazineService.toCategoryDTO(magazineService.getCategoryById(id));
        } catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public CategoryResponseDTO addCategory(@Argument("input") CategoryInputDTO input) {
        return magazineService.toCategoryDTO(
                magazineService.addCategory(input.getName())
        );
    }

    @MutationMapping
    public CategoryResponseDTO updateCategory(@Argument Long id, @Argument("input") CategoryInputDTO input) {
        return magazineService.toCategoryDTO(
                magazineService.updateCategory(id, input.getName())
        );
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return magazineService.deleteCategory(id);
    }

    /* ---------- Series ---------- */

    @QueryMapping
    public List<SeriesResponseDTO> getSeries() {
        return magazineService.getAllSeries()
                .stream()
                .map(magazineService::toSeriesDTO)
                .toList();
    }

    @QueryMapping
    public SeriesResponseDTO getSeriesById(@Argument Long id) {
        try {
            return magazineService.toSeriesDTO(magazineService.getSeriesById(id));
        } catch (CatalogNotFoundException e) {
            return null;
        }
    }

    @MutationMapping
    public SeriesResponseDTO addSeries(@Argument("input") SeriesInputDTO input) {
        return magazineService.toSeriesDTO(
                magazineService.addSeries(input.getName())
        );
    }

    @MutationMapping
    public SeriesResponseDTO updateSeries(@Argument Long id, @Argument("input") SeriesInputDTO input) {
        return magazineService.toSeriesDTO(
                magazineService.updateSeries(id, input.getName())
        );
    }

    @MutationMapping
    public Boolean deleteSeries(@Argument Long id) {
        return magazineService.deleteSeries(id);
    }
}
