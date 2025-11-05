package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionControllerTest {

    private MockWebServer subscriptionServer;
    private HttpGraphQlTester graphQlTester;

    @BeforeAll
    void setup() throws IOException {
        subscriptionServer = new MockWebServer();
        subscriptionServer.start(8081);

        String baseUrl = "http://localhost:" + subscriptionServer.getPort() + "/graphql";

        WebTestClient client = WebTestClient
                .bindToServer()
                .baseUrl(baseUrl)
                .build();

        graphQlTester = HttpGraphQlTester.create(client);
    }

    @AfterAll
    void teardown() throws IOException {
        subscriptionServer.shutdown();
    }

    @BeforeEach
    void mockResponse() {
        subscriptionServer.enqueue(new MockResponse()
                .setBody("""
                    {
                      "data": {
                        "getUserSubscriptions": [
                          {
                            "id": "100",
                            "userId": "1",
                            "magazineId": "7",
                            "status": "ACTIVE"
                          }
                        ]
                      }
                    }
                    """)
                .addHeader("Content-Type", "application/json"));
    }

    @Test
    void testGetUserSubscriptions() throws InterruptedException {
        graphQlTester.document("""
                query GetUserSubscriptions($id: String!) {
                    getUserSubscriptions(id: $id) {
                        id
                        userId
                        magazineId
                        status
                    }
                }
                """)
                .variable("id", "1")
                .execute()
                .path("data.getUserSubscriptions[0].status")
                .entity(String.class)
                .isEqualTo("ACTIVE");

        RecordedRequest recordedRequest = subscriptionServer.takeRequest();
        assertEquals("/graphql", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());

        String requestBody = recordedRequest.getBody().readUtf8();
        assertTrue(requestBody.contains("getUserSubscriptions"));
        assertTrue(requestBody.contains("\"id\":\"1\""));
    }
}
