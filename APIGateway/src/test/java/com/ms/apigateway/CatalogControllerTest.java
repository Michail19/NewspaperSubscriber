package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.io.IOException;

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogControllerTest {

    private MockWebServer catalogServer;

    @Autowired
    private GraphQlTester graphQlTester;

    @BeforeAll
    void setup() throws IOException {
        catalogServer = new MockWebServer();
        catalogServer.start(8083);
    }

    @AfterAll
    void teardown() throws IOException {
        catalogServer.shutdown();
    }

    @BeforeEach
    void mockResponse() {
        catalogServer.enqueue(new MockResponse()
                .setBody("""
                    {
                      "data": {
                        "getCatalogById": {
                          "id": "42",
                          "title": "Tech Weekly",
                          "description": "AI and Tech news",
                          "price": 9.99
                        }
                      }
                    }
                    """)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void testGetCatalogById() {
        graphQlTester.document("""
                query {
                    getCatalogById(id: "42") {
                        id title description price
                    }
                }
                """)
                .execute()
                .path("getCatalogById.id").entity(String.class).isEqualTo("42")
                .path("getCatalogById.title").entity(String.class).isEqualTo("Tech Weekly")
                .path("getCatalogById.price").entity(Double.class).isEqualTo(9.99);
    }
}
