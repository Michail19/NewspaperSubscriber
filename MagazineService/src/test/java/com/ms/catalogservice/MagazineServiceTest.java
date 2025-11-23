package com.ms.catalogservice;

import com.ms.catalogservice.exception.CatalogNotFoundException;
import com.ms.catalogservice.exception.CategoryNotFoundException;
import com.ms.catalogservice.exception.SeriesNotFoundException;
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
                .isInstanceOf(CatalogNotFoundException.class)
                .hasMessageContaining("Catalog not found with id: 1");
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
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 1");
    }

    @Test
    void addCatalog_seriesNotFound_shouldThrow() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.addCatalog(new Catalog(), 1L, 1L))
                .isInstanceOf(SeriesNotFoundException.class)
                .hasMessageContaining("Series not found with id: 1");
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
    void updateCatalog_catalogNotFound_shouldThrow() {
        when(catalogRepository.findById(1L)).thenReturn(Optional.empty());

        Catalog update = new Catalog();
        update.setTitle("Updated");

        assertThatThrownBy(() -> magazineService.updateCatalog(1L, update, null, null))
                .isInstanceOf(CatalogNotFoundException.class)
                .hasMessageContaining("Catalog not found with id: 1");
    }

    @Test
    void updateCatalog_categoryNotFound_shouldThrow() {
        when(catalogRepository.findById(1L)).thenReturn(Optional.of(catalog));
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Catalog update = new Catalog();
        update.setTitle("Updated");

        assertThatThrownBy(() -> magazineService.updateCatalog(1L, update, 1L, null))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 1");
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
                .isInstanceOf(CatalogNotFoundException.class)
                .hasMessageContaining("Catalog not found with id: 1");
    }

    /* ---------- Category ---------- */

    @Test
    void getAllCategories_shouldReturnList() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        var result = magazineService.getAllCategories();
        assertThat(result).hasSize(1);
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        var result = magazineService.getCategoryById(1L);
        assertThat(result).isEqualTo(category);
    }

    @Test
    void getCategoryById_notFound_shouldThrow() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.getCategoryById(1L))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 1");
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
    void updateCategory_notFound_shouldThrow() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.updateCategory(1L, "UpdatedName"))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 1");
    }

    @Test
    void deleteCategory_shouldReturnTrue() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        boolean result = magazineService.deleteCategory(1L);
        assertThat(result).isTrue();
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void deleteCategory_notFound_shouldThrow() {
        when(categoryRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> magazineService.deleteCategory(1L))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: 1");
    }

    /* ---------- Series ---------- */

    @Test
    void getAllSeries_shouldReturnList() {
        when(seriesRepository.findAll()).thenReturn(List.of(series));
        var result = magazineService.getAllSeries();
        assertThat(result).hasSize(1);
    }

    @Test
    void getSeriesById_shouldReturnSeries() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        var result = magazineService.getSeriesById(1L);
        assertThat(result).isEqualTo(series);
    }

    @Test
    void getSeriesById_notFound_shouldThrow() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.getSeriesById(1L))
                .isInstanceOf(SeriesNotFoundException.class)
                .hasMessageContaining("Series not found with id: 1");
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
    void updateSeries_notFound_shouldThrow() {
        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> magazineService.updateSeries(1L, "UpdatedSeries"))
                .isInstanceOf(SeriesNotFoundException.class)
                .hasMessageContaining("Series not found with id: 1");
    }

    @Test
    void deleteSeries_shouldReturnTrue() {
        when(seriesRepository.existsById(1L)).thenReturn(true);
        boolean result = magazineService.deleteSeries(1L);
        assertThat(result).isTrue();
        verify(seriesRepository).deleteById(1L);
    }

    @Test
    void deleteSeries_notFound_shouldThrow() {
        when(seriesRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> magazineService.deleteSeries(1L))
                .isInstanceOf(SeriesNotFoundException.class)
                .hasMessageContaining("Series not found with id: 1");
    }

    /* ---------- DTO Conversion Tests ---------- */

    @Test
    void toCatalogDTO_shouldConvertCorrectly() {
        catalog.setCategory(category);
        catalog.setSeries(series);

        var result = magazineService.toCatalogDTO(catalog);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test");
        assertThat(result.getCategory()).isNotNull();
        assertThat(result.getSeries()).isNotNull();
    }

    @Test
    void toCategoryDTO_shouldConvertCorrectly() {
        var result = magazineService.toCategoryDTO(category);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Category1");
    }

    @Test
    void toSeriesDTO_shouldConvertCorrectly() {
        var result = magazineService.toSeriesDTO(series);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Series1");
    }

    @Test
    void toCategoryDTO_withNull_shouldReturnNull() {
        var result = magazineService.toCategoryDTO(null);
        assertThat(result).isNull();
    }

    @Test
    void toSeriesDTO_withNull_shouldReturnNull() {
        var result = magazineService.toSeriesDTO(null);
        assertThat(result).isNull();
    }
}
