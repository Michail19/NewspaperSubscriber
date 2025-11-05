package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogControllerTest {

    private MockWebServer catalogServer;
    private HttpGraphQlTester graphQlTester;

    @BeforeAll
    void setup() throws IOException {
        catalogServer = new MockWebServer();
        catalogServer.start(8083);

        String baseUrl = "http://localhost:" + catalogServer.getPort() + "/graphql";

        WebTestClient client = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl)
                .build();

        graphQlTester = HttpGraphQlTester.create(client);
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
    void testGetCatalogById() throws InterruptedException {
        graphQlTester.document("""
                query GetCatalogById($id: String!) {
                    getCatalogById(id: $id) {
                        id
                        title
                        description
                        price
                    }
                }
                """)
                .variable("id", "42")
                .execute()
                .path("data.getCatalogById.id").entity(String.class).isEqualTo("42")
                .path("data.getCatalogById.title").entity(String.class).isEqualTo("Tech Weekly")
                .path("data.getCatalogById.description").entity(String.class).isEqualTo("AI and Tech news")
                .path("data.getCatalogById.price").entity(Double.class).isEqualTo(9.99);

        RecordedRequest recordedRequest = catalogServer.takeRequest();
        assertEquals("/graphql", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());

        String requestBody = recordedRequest.getBody().readUtf8();
        assertTrue(requestBody.contains("getCatalogById"));
        assertTrue(requestBody.contains("\"id\":\"42\""));
    }
}
