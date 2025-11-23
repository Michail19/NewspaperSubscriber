package com.ms.catalogservice;

import com.ms.catalogservice.model.Catalog;
import com.ms.catalogservice.model.Category;
import com.ms.catalogservice.model.Series;
import com.ms.catalogservice.repository.CatalogRepository;
import com.ms.catalogservice.repository.CategoryRepository;
import com.ms.catalogservice.repository.SeriesRepository;
import com.ms.catalogservice.service.MagazineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MagazineServiceTest {

    @Mock
    private CatalogRepository catalogRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private MagazineService magazineService;

    private Catalog catalog;
    private Category category;
    private Series series;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        catalog = new Catalog();
        catalog.setId(1L);
        catalog.setTitle("Test");
        catalog.setPrice(100.0);

        category = new Category();
        category.setId(1L);
        category.setName("Category1");

        series = new Series();
        series.setId(1L);
        series.setName("Series1");
    }

    /* ---------- Catalog ---------- */

    @Test
    void getAllCatalogs_shouldReturnList() {
        when(catalogRepository.findAll()).thenReturn(List.of(catalog));
        var result = magazineService.getAllCatalogs();
        assertThat(result).hasSize(1);
        verify(catalogRepository).findAll();
    }

    @Test
    void getCatalogById_shouldReturnCatalog() {
        when(catalogRepository.findById(1L)).thenReturn(Optional.of(catalog));
        var result = magazineService.getCatalogById(1L);
        assertThat(result).isEqualTo(catalog);
    }

    @Test
    void getCatalogById_notFound_shouldThrow() {
        when(catalogRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.getCatalogById(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Каталог не найден");
    }

    @Test
    void addCatalog_withCategoryAndSeries_shouldSave() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(catalogRepository.save(any())).thenReturn(catalog);

        var result = magazineService.addCatalog(new Catalog(), 1L, 1L);

        assertThat(result).isNotNull();
        verify(catalogRepository).save(any(Catalog.class));
    }

    @Test
    void addCatalog_categoryNotFound_shouldThrow() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.addCatalog(new Catalog(), 1L, null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Категория не найдена");
    }

    @Test
    void updateCatalog_shouldUpdateAndSave() {
        when(catalogRepository.findById(1L)).thenReturn(Optional.of(catalog));
        when(catalogRepository.save(any())).thenReturn(catalog);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));

        Catalog update = new Catalog();
        update.setTitle("Updated");
        update.setDescription("New desc");
        update.setPrice(200.0);
        update.setLink("newlink");

        var result = magazineService.updateCatalog(1L, update, 1L, 1L);

        assertThat(result.getTitle()).isEqualTo("Updated");
        verify(catalogRepository).save(any(Catalog.class));
    }

    @Test
    void deleteCatalog_shouldReturnTrue() {
        when(catalogRepository.existsById(1L)).thenReturn(true);
        boolean result = magazineService.deleteCatalog(1L);
        assertThat(result).isTrue();
        verify(catalogRepository).deleteById(1L);
    }

    @Test
    void deleteCatalog_notFound_shouldThrow() {
        when(catalogRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> magazineService.deleteCatalog(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Каталог не найден");
    }

    /* ---------- Category ---------- */

    @Test
    void getAllCategories_shouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        var result = magazineService.getAllCategories();
        assertThat(result).hasSize(1);
    }

    @Test
    void addCategory_shouldSave() {
        when(categoryRepository.save(any())).thenReturn(category);
        var result = magazineService.addCategory("New");
        assertThat(result.getName()).isEqualTo("Category1");
    }

    @Test
    void updateCategory_shouldUpdate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);
        var result = magazineService.updateCategory(1L, "UpdatedName");
        assertThat(result.getName()).isEqualTo("UpdatedName");
    }

    @Test
    void deleteCategory_shouldReturnTrue() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        boolean result = magazineService.deleteCategory(1L);
        assertThat(result).isTrue();
        verify(categoryRepository).deleteById(1L);
    }

    /* ---------- Series ---------- */

    @Test
    void getAllSeries_shouldReturnList() {
        when(seriesRepository.findAll()).thenReturn(List.of(series));
        var result = magazineService.getAllSeries();
        assertThat(result).hasSize(1);
    }

    @Test
    void addSeries_shouldSave() {
        when(seriesRepository.save(any())).thenReturn(series);
        var result = magazineService.addSeries("Series1");
        assertThat(result).isNotNull();
    }

    @Test
    void updateSeries_shouldUpdate() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(seriesRepository.save(any())).thenReturn(series);
        var result = magazineService.updateSeries(1L, "UpdatedSeries");
        assertThat(result.getName()).isEqualTo("UpdatedSeries");
    }

    @Test
    void deleteSeries_shouldReturnTrue() {
        when(seriesRepository.existsById(1L)).thenReturn(true);
        boolean result = magazineService.deleteSeries(1L);
        assertThat(result).isTrue();
        verify(seriesRepository).deleteById(1L);
    }
}
