package com.ms.apigateway;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionControllerTest {

    private MockWebServer subscriptionServer;
    private HttpGraphQlTester graphQlTester;

    @LocalServerPort
    private int port;

    @BeforeAll
    void setup() throws IOException {
        subscriptionServer = new MockWebServer();
        subscriptionServer.start(8081);

        WebTestClient client = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/graphql")
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
    void testGetUserSubscriptions() {
        graphQlTester.document("""
                query {
                    getUserSubscriptions(id: "1") {
                        id userId magazineId status
                    }
                }
                """)
                .execute()
                .path("getUserSubscriptions[0].status")
                .entity(String.class)
                .isEqualTo("ACTIVE");
    }
}
