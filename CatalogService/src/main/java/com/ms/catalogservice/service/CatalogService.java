package com.ms.catalogservice.service;

import com.ms.catalogservice.exception.CatalogNotFoundException;
import com.ms.catalogservice.exception.CategoryNotFoundException;
import com.ms.catalogservice.exception.SeriesNotFoundException;
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

    /* ---------- Catalog ---------- */

    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    public Catalog getCatalogById(Long id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new CatalogNotFoundException("Catalog not found with id: " + id));
    }

    public Catalog addCatalog(Catalog catalog, Long categoryId, Long seriesId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));
            catalog.setCategory(category);
        }

        if (seriesId != null) {
            Series series = seriesRepository.findById(seriesId)
                    .orElseThrow(() -> new SeriesNotFoundException("Series not found with id: " + seriesId));
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
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));
            catalog.setCategory(category);
        }

        if (seriesId != null) {
            Series series = seriesRepository.findById(seriesId)
                    .orElseThrow(() -> new SeriesNotFoundException("Series not found with id: " + seriesId));
            catalog.setSeries(series);
        }

        return catalogRepository.save(catalog);
    }

    public boolean deleteCatalog(Long id) {
        if (!catalogRepository.existsById(id)) {
            throw new CatalogNotFoundException("Catalog not found with id: " + id);
        }
        catalogRepository.deleteById(id);
        return true;
    }

    /* ---------- Category ---------- */

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        category.setName(newName);
        return categoryRepository.save(category);
    }

    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return true;
    }

    /* ---------- Series ---------- */

    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    public Series addSeries(String name) {
        Series series = new Series();
        series.setName(name);
        return seriesRepository.save(series);
    }

    public Series updateSeries(Long id, String newName) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new SeriesNotFoundException("Series not found with id: " + id));
        series.setName(newName);
        return seriesRepository.save(series);
    }

    public boolean deleteSeries(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new SeriesNotFoundException("Series not found with id: " + id);
        }
        seriesRepository.deleteById(id);
        return true;
    }
}
