package com.ms.catalogservice.service;

import com.ms.catalogservice.model.Catalog;
import com.ms.catalogservice.model.Category;
import com.ms.catalogservice.model.Series;
import com.ms.catalogservice.repository.CatalogRepository;
import com.ms.catalogservice.repository.CategoryRepository;
import com.ms.catalogservice.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository,
                          CategoryRepository categoryRepository,
                          SeriesRepository seriesRepository) {
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.seriesRepository = seriesRepository;
    }

    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    public Catalog getCatalogById(Long id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Каталог не найден"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Catalog addCatalog(Catalog catalog, Long categoryId, Long seriesId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
            catalog.setCategory(category);
        }

        if (seriesId != null) {
            Series series = seriesRepository.findById(seriesId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Серия не найдена"));
            catalog.setSeries(series);
        }

        return catalogRepository.save(catalog);
    }

    public Catalog updateCatalog(Long id, Catalog updatedCatalog, Long categoryId, Long seriesId) {
        Catalog catalog = getCatalogById(id);

        catalog.setTitle(updatedCatalog.getTitle());
        catalog.setDescription(updatedCatalog.getDescription());
        catalog.setPrice(updatedCatalog.getPrice());
        catalog.setLink(updatedCatalog.getLink());

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
            catalog.setCategory(category);
        }

        if (seriesId != null) {
            Series series = seriesRepository.findById(seriesId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Серия не найдена"));
            catalog.setSeries(series);
        }

        return catalogRepository.save(catalog);
    }

    public boolean deleteCatalog(Long id) {
        if (!catalogRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Каталог не найден");
        }
        catalogRepository.deleteById(id);
        return true;
    }
}
