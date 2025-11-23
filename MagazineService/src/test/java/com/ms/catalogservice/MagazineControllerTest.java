package com.ms.catalogservice;

import com.ms.catalogservice.controller.MagazineController;
import com.ms.catalogservice.dto.CatalogResponseDTO;
import com.ms.catalogservice.model.Catalog;
import com.ms.catalogservice.service.MagazineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@GraphQlTest(MagazineController.class)
class MagazineControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private MagazineService magazineService;

    @Test
    void getCatalogs_shouldReturnList() {
        // Создаем DTO объект вместо entity
        CatalogResponseDTO catalogDTO = new CatalogResponseDTO();
        catalogDTO.setId(1L);
        catalogDTO.setTitle("Catalog1");

        when(magazineService.getAllCatalogs()).thenReturn(List.of(new Catalog()));
        when(magazineService.toCatalogDTO(any(Catalog.class))).thenReturn(catalogDTO);

        String query = """
            query {
              getCatalogs {
                id
                title
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("getCatalogs")
                .entityList(CatalogResponseDTO.class)
                .hasSize(1)
                .path("getCatalogs[0].title")
                .entity(String.class)
                .isEqualTo("Catalog1");
    }

    @Test
    void addCatalog_shouldReturnCatalog() {
        // Создаем DTO объект вместо entity
        CatalogResponseDTO catalogDTO = new CatalogResponseDTO();
        catalogDTO.setId(1L);
        catalogDTO.setTitle("NewCatalog");

        Catalog catalog = new Catalog();
        catalog.setId(1L);
        catalog.setTitle("NewCatalog");

        when(magazineService.addCatalog(any(Catalog.class), eq(1L), eq(2L))).thenReturn(catalog);
        when(magazineService.toCatalogDTO(any(Catalog.class))).thenReturn(catalogDTO);

        String mutation = """
            mutation {
              addCatalog(input: {
                title: "NewCatalog",
                description: "Desc",
                price: 10.5,
                link: "url",
                categoryId: 1,
                seriesId: 2
              }) {
                id
                title
              }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("addCatalog.title")
                .entity(String.class)
                .isEqualTo("NewCatalog");
    }
}
