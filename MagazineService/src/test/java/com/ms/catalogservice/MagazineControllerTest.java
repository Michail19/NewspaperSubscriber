package com.ms.catalogservice;

import com.ms.catalogservice.controller.MagazineController;
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
        Catalog catalog = new Catalog();
        catalog.setId(1L);
        catalog.setTitle("Catalog1");

        when(magazineService.getAllCatalogs()).thenReturn(List.of(catalog));

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
                .path("getCatalogs[0].title")
                .entity(String.class)
                .isEqualTo("Catalog1");
    }

    @Test
    void addCatalog_shouldReturnCatalog() {
        Catalog catalog = new Catalog();
        catalog.setId(1L);
        catalog.setTitle("NewCatalog");

        when(magazineService.addCatalog(any(Catalog.class), eq(1L), eq(2L))).thenReturn(catalog);

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
